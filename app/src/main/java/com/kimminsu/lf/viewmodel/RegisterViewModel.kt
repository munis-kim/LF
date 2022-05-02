package com.kimminsu.lf.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class RegisterViewModel : ViewModel(){
    var userId = MutableLiveData("")
    var passwd = MutableLiveData("")
    var checkPasswd = MutableLiveData("")
    var nickname = MutableLiveData("")
    var isRegister = MutableLiveData(-1)

    private lateinit var auth: FirebaseAuth

    fun onRegister(){
        val userId = userId.value
        val passwd = passwd.value
        val checkPasswd = checkPasswd.value
        val nickname = nickname.value

        if(userId?.isEmpty() == true){
            isRegister.value = 1
            return
        } else if(passwd?.isEmpty() == true){
            isRegister.value = 2
            return
        } else if(passwd?.length!! < 6) {
            isRegister.value = 3

        } else if(checkPasswd?.isEmpty() == true){
            isRegister.value = 4
            return
        } else if(!checkPasswd.equals(passwd)){
            isRegister.value = 5
            return
        } else if(nickname?.isEmpty() == true){
            isRegister.value = 6
        }

        auth = Firebase.auth
        if (userId != null && passwd != null) {
            auth.createUserWithEmailAndPassword(userId, passwd)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        isRegister.value = 0
                    } else{
                        try{
                            task.result;
                        } catch(e:Exception){
                            val tmp: String = e.toString()
                            if(tmp.endsWith("account.")){
                                isRegister.value = 7
                            }
                        }
                    }
                }
        }
    }
}