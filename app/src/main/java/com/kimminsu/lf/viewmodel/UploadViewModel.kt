package com.kimminsu.lf.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kimminsu.lf.repository.AuthRepository
import com.kimminsu.lf.utils.SingleLiveEvent

class UploadViewModel : ViewModel() {
    var titleLiveData = MutableLiveData("")
    var contentLiveData = MutableLiveData("")


    fun onUpload(){

    }

}