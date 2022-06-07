package com.kimminsu.lf.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kimminsu.lf.data.User
import java.lang.Exception

class AuthRepository private constructor() {
    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        @JvmStatic
        fun getInstance(): AuthRepository = instance ?: synchronized(this) {
            instance ?: AuthRepository().also {
                instance = it
            }
        }
    }

    fun login(userId: String, password: String, callback: (Int) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(userId, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(0)
                } else {
                    if (task.exception != null) {
                        callback(3)
                    }
                }
            }
        callback(-1)
    }

    fun register(
        userId: String,
        password: String,
        nickname: String,
        name: String,
        callback: (Int) -> Unit
    ) {
        Firebase.auth.createUserWithEmailAndPassword(userId, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
                    val user = User(userId, nickname, name)
                    db.collection("users").document(userId)
                        .set(user)
                        .addOnSuccessListener { callback(0) }
                } else {
                    try {
                        task.result;
                    } catch (e: Exception) {
                        val tmp: String = e.toString()
                        if (tmp.endsWith("account.")) {
                            callback(8)
                        }
                    }
                }
            }
    }
}