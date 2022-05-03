package com.kimminsu.lf.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kimminsu.lf.data.User
import com.kimminsu.lf.utils.SingleLiveEvent
import java.lang.Exception

class RegisterViewModel : ViewModel() {
    var userIdLiveData = MutableLiveData("")
    var passwordLiveData = MutableLiveData("")
    var checkPasswordLiveData = MutableLiveData("")
    var nicknameLiveData = MutableLiveData("")
    var nameLiveData = MutableLiveData("")
    var isRegisterLiveData = MutableLiveData(-1)
    var isGoLoginLiveData = SingleLiveEvent<Any>()
    private lateinit var auth: FirebaseAuth

    val GoLogin: LiveData<Any>
        get() = isGoLoginLiveData

    fun onRegister() {
        val userId = userIdLiveData.value!!
        val password = passwordLiveData.value!!
        val checkPasswd = checkPasswordLiveData.value!!
        val nickname = nicknameLiveData.value!!
        val name = nameLiveData.value!!

        if (userId.isEmpty()) {
            isRegisterLiveData.value = 1
            return
        } else if (password.isEmpty()) {
            isRegisterLiveData.value = 2
            return
        } else if (password.length < 6) {
            isRegisterLiveData.value = 3

        } else if (checkPasswd.isEmpty()) {
            isRegisterLiveData.value = 4
            return
        } else if (!checkPasswd.equals(password)) {
            isRegisterLiveData.value = 5
            return
        } else if (nickname.isEmpty()) {
            isRegisterLiveData.value = 6
            return
        } else if (name.isEmpty()) {
            isRegisterLiveData.value = 7
            return
        }
        Firebase.auth.createUserWithEmailAndPassword(userId, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
                    val user = User(userId, nickname, name)
                    db.collection("users").document(userId)
                        .set(user)
                        .addOnSuccessListener { isRegisterLiveData.value = 0 }
                } else {
                    try {
                        task.result;
                    } catch (e: Exception) {
                        val tmp: String = e.toString()
                        if (tmp.endsWith("account.")) {
                            isRegisterLiveData.value = 8
                        }
                    }
                }
            }
    }

    fun onGoLogin() {
        isGoLoginLiveData.call()
    }

    fun clearLiveData() {
        userIdLiveData.value = ""
        passwordLiveData.value = ""
        checkPasswordLiveData.value = ""
        nicknameLiveData.value = ""
        nameLiveData.value = ""
        isRegisterLiveData.value = -1
    }
}