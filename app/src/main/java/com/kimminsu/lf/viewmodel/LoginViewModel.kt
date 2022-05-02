package com.kimminsu.lf.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {
    var userId = MutableLiveData("")
    var passwd = MutableLiveData("")
    var isLogin = MutableLiveData(-1)
    var isGoRegister = MutableLiveData(false)
    private lateinit var auth: FirebaseAuth

    fun onLogin(){
        val userId = userId.value
        val passwd = passwd.value

        if(userId?.isEmpty() == true) {
            isLogin.value = 1
            return
        } else if(passwd?.isEmpty() == true){
            isLogin.value = 2
            return
        }

        auth = Firebase.auth
        if (userId != null && passwd != null) {
            auth.signInWithEmailAndPassword(userId, passwd)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        isLogin.value = 0
                    }
                    else{
                        if(task.exception != null){
                            isLogin.value = 3
                        }
                    }
                }
        }
    }

    fun onGoRegister(){
        isGoRegister.value = true
    }
}