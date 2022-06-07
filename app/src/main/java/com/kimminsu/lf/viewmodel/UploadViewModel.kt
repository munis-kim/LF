package com.kimminsu.lf.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kimminsu.lf.repository.AuthRepository
import com.kimminsu.lf.utils.SingleLiveEvent

class UploadViewModel : ViewModel() {
    var titleLiveData = MutableLiveData("")
    var contentLiveData = MutableLiveData("")
    var isImageUploadLiveData = SingleLiveEvent<Any>()
    var isCameraLiveData = SingleLiveEvent<Any>()

    val GoImageUpload: LiveData<Any>
        get() = isImageUploadLiveData

    val GoCamera: LiveData<Any>
        get() = isCameraLiveData

    fun onCamera() {
        isCameraLiveData.call()
    }

    fun onUpload() {
        isImageUploadLiveData.call()
    }

}