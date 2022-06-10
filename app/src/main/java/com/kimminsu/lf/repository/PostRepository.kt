package com.kimminsu.lf.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.net.Uri.parse
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.kimminsu.lf.data.Post
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.logging.Level.parse
import kotlin.collections.ArrayList

class PostRepository private constructor() {
    companion object {
        @Volatile
        private var instance: PostRepository? = null

        @JvmStatic
        fun getInstance(): PostRepository = instance ?: synchronized(this) {
            instance ?: PostRepository().also {
                instance = it
            }
        }
    }


    fun uploadImage(userId: String, uploadTime: String, image: Uri, callback: (Uri) -> Unit) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference

        storageRef.child("post_img/$userId/$uploadTime/1.jpg")
            .putFile(image)
            .addOnSuccessListener { taskSnapShot ->
                taskSnapShot.metadata?.reference?.downloadUrl?.addOnSuccessListener { it ->
                    callback(it)
                }
            }
    }

    fun uploadPost(
        nickname: String,
        title: String,
        content: String,
        catalog: String,
        uploadTime: String,
        image: Uri,
        callback: (Int) -> Unit
    ) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val post = Post(nickname, title, content, catalog, uploadTime, image)
        db.collection("posts").add(post)
            .addOnSuccessListener { callback(0) }
            .addOnFailureListener { callback(4) }
    }

    fun loadMyPost(userId: String, callback: (ArrayList<Post>) -> Unit){
        val storage = FirebaseFirestore.getInstance()
        val postList = ArrayList<Post>()
        storage.collection("posts")
            .orderBy("uploadTime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(userId != document["nickname"] as String) continue
                    val uri = Uri.parse(document["image"] as String)
                    val post = Post(
                        document["nickname"] as String,
                        document["title"] as String,
                        document["content"] as String,
                        document["catalog"] as String,
                        document["uploadTime"] as String,
                        uri
                    )
                    postList.add(post)
                }
                callback(postList)
            }
    }

    fun loadPost(callback: (ArrayList<Post>) -> Unit){
        val storage = FirebaseFirestore.getInstance()
        val postList = ArrayList<Post>()
        storage.collection("posts")
            .orderBy("uploadTime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val uri = Uri.parse(document["image"] as String)
                    val post = Post(
                        document["nickname"] as String,
                        document["title"] as String,
                        document["content"] as String,
                        document["catalog"] as String,
                        document["uploadTime"] as String,
                        uri
                    )
                    postList.add(post)
                }
                callback(postList)
            }
    }

    fun searchPost(search: String, callback: (ArrayList<Post>) -> Unit){
        val storage = FirebaseFirestore.getInstance()
        val postList = ArrayList<Post>()
        storage.collection("posts")
            .orderBy("uploadTime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val str = document["title"] as String
                    if(!str.contains(search)) continue
                    val uri = Uri.parse(document["image"] as String)
                    val post = Post(
                        document["nickname"] as String,
                        document["title"] as String,
                        document["content"] as String,
                        document["catalog"] as String,
                        document["uploadTime"] as String,
                        uri
                    )
                    postList.add(post)
                }
                callback(postList)
            }
    }
}