package com.kimminsu.lf.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.kimminsu.lf.repository.AuthRepository
import com.kimminsu.lf.utils.SingleLiveEvent

class LoginViewModel : ViewModel() {
    var authRepository = AuthRepository.getInstance()
    var userIdLiveData = MutableLiveData("")
    var passwordLiveData = MutableLiveData("")
    var isLoginLiveData = MutableLiveData(-1)
    var isGoRegisterLiveData = SingleLiveEvent<Any>()
    private lateinit var auth: FirebaseAuth

    val GoRegister: LiveData<Any>
        get() = isGoRegisterLiveData

    fun onLogin() {
        val userId = userIdLiveData.value!!
        val password = passwordLiveData.value!!

        if (userId.isEmpty()) {
            isLoginLiveData.value = 1
            return
        } else if (password.isEmpty()) {
            isLoginLiveData.value = 2
            return
        }
        authRepository.login(userId, password) {code ->
            run {
                isLoginLiveData.value = code
            }
        }
    }

    fun onGoRegister() {
        isGoRegisterLiveData.call()
    }
}