package com.kimminsu.lf.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kimminsu.lf.utils.SingleLiveEvent

class MainViewModel : ViewModel(){
    var isGoUploadLiveData = SingleLiveEvent<Any>()

    val GoUpload: LiveData<Any>
        get() = isGoUploadLiveData

    fun onGoUpload() {
        isGoUploadLiveData.call()
    }
}