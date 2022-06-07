package com.kimminsu.lf.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kimminsu.lf.repository.AuthRepository
import com.kimminsu.lf.utils.SingleLiveEvent

class MyProfileViewModel: ViewModel() {
    var authRepository = AuthRepository.getInstance()

    fun onLogout(){
        Firebase.auth.signOut()
    }
}