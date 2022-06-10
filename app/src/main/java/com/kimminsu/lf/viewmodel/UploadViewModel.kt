package com.kimminsu.lf.viewmodel

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kimminsu.lf.repository.AuthRepository
import com.kimminsu.lf.utils.SingleLiveEvent
import java.io.File
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UploadViewModel : ViewModel() {
    var titleLiveData = MutableLiveData("")
    var contentLiveData = MutableLiveData("")
    var isGalleryLiveData = SingleLiveEvent<Any>()
    var isCameraLiveData = SingleLiveEvent<Any>()
    val imageList = mutableListOf<Uri>()
    private val imageListLiveData = MutableLiveData<List<Uri>>()
    val imageLiveData: LiveData<List<Uri>> = imageListLiveData

    val GoImageUpload: LiveData<Any>
        get() = isGalleryLiveData

    val GoCamera: LiveData<Any>
        get() = isCameraLiveData

    fun onCamera() {
        isCameraLiveData.call()
    }

    fun onGallery() {
        isGalleryLiveData.call()
    }

    fun onUpload(){}

    fun onImageUpload(data: Uri){
        imageList.add(data)
        imageListLiveData.value = imageList
    }

    fun setImage(data: Bitmap){
        val fileName = "${System.currentTimeMillis()}.jpg"

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

        }
    }
/*
    @SuppressLint("SimpleDateFormat")
    private fun createImageFile() : File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

    }
*/
}