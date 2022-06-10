package com.kimminsu.lf.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kimminsu.lf.data.Post
import com.kimminsu.lf.repository.PostRepository
import com.kimminsu.lf.utils.SingleLiveEvent

class MainViewModel : ViewModel() {
    private var postRepository = PostRepository.getInstance()
    var isGoUploadLiveData = SingleLiveEvent<Any>()
    var postList = ArrayList<Post>()
    var isLoadLiveData = SingleLiveEvent<Any>()

    val GoLoad: LiveData<Any>
        get() = isLoadLiveData

    val GoUpload: LiveData<Any>
        get() = isGoUploadLiveData

    fun onGoUpload() {
        isGoUploadLiveData.call()
    }

    fun loadPost() {
        postRepository.loadPost() { result ->
            postList = result
            isLoadLiveData.call()
        }
    }
}