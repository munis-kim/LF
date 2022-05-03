package com.kimminsu.lf.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kimminsu.lf.LoginStateListener
import kotlinx.coroutines.delay

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

    fun login(userId: String, password: String): Int {
        var result = -1
        Firebase.auth.signInWithEmailAndPassword(userId, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result = 0
                } else {
                    if (task.exception != null) {
                        result = 3
                    }
                }
            }
        return result
    }
}