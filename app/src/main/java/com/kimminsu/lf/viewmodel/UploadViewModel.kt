package com.kimminsu.lf.viewmodel

import android.net.Uri
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kimminsu.lf.repository.AuthRepository
import com.kimminsu.lf.utils.SingleLiveEvent

class UploadViewModel : ViewModel() {
    var titleLiveData = MutableLiveData("")
    var contentLiveData = MutableLiveData("")
    var isImageUploadLiveData = MutableLiveData(-1)
    var isGalleryLiveData = SingleLiveEvent<Any>()
    var isCameraLiveData = SingleLiveEvent<Any>()

    val GoGallery: LiveData<Any>
        get() = isGalleryLiveData

    val GoCamera: LiveData<Any>
        get() = isCameraLiveData

    fun onCamera() {
        isCameraLiveData.call()
    }

    fun onGallery() {
        isGalleryLiveData.call()
    }

    fun onUpload(){

    }

    fun setImage(){

    }

    fun onImageUpload(data: Uri){
        val temp = ExifInterface.TAG_GPS_LATITUDE
        Log.d("data", "$temp")
    }


}