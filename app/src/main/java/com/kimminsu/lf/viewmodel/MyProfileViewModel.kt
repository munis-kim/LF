package com.kimminsu.lf.viewmodel

import androidx.lifecycle.ViewModel
import com.kimminsu.lf.repository.AuthRepository
import com.kimminsu.lf.utils.SingleLiveEvent

class MyProfileViewModel: ViewModel() {
    var authRepository = AuthRepository.getInstance()

    fun onLogout(){
        authRepository.logout()
    }
}