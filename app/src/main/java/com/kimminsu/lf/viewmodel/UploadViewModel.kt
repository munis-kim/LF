package com.kimminsu.lf.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.kimminsu.lf.UserInfo
import com.kimminsu.lf.repository.AuthRepository
import com.kimminsu.lf.repository.PostRepository
import com.kimminsu.lf.utils.SingleLiveEvent
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class UploadViewModel : ViewModel() {
    private var postRepository = PostRepository.getInstance()
    var titleLiveData = MutableLiveData("")
    var contentLiveData = MutableLiveData("")
    var catalogLiveData = MutableLiveData("")
    var imageLiveData = MutableLiveData<Uri?>()
    var isUploadLiveData = MutableLiveData(-1)
    var isLocationLiveData = MutableLiveData(-1)
    var isImageUploadLiveData = MutableLiveData(false)
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

    fun setImage(data: Uri?){
        imageLiveData.value = data
        isImageUploadLiveData.value = true
        catalogLiveData.value = "hi"
    }

    @SuppressLint("SimpleDateFormat")
    fun onUpload(){
        val userId = UserInfo.userId!!
        val title = titleLiveData.value!!
        val content = contentLiveData.value!!
        val catalog = catalogLiveData.value!!
        val uploadTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())
        var image = imageLiveData.value!!

        if(title.isEmpty()){
            isUploadLiveData.value = 1
            return
        } else if(content.isEmpty()){
            isUploadLiveData.value = 2
            return
        } else if(isImageUploadLiveData.value == false){
            isUploadLiveData.value = 3
            return
        }

        postRepository.uploadImage(userId, uploadTime, image) { code->
            run{
                image = code
                postRepository.uploadPost(userId, title, content, catalog, uploadTime, image) {code ->
                    run{
                        isUploadLiveData.value = code
                    }
                }
            }
        }
    }

    fun getImageUri(inContext: Context?, inImage: Bitmap?): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext?.contentResolver, inImage, "Title" + " - " + Calendar.getInstance().time, null)
        return Uri.parse(path)
    }

    fun clearLiveData(){
        titleLiveData.value = ""
        contentLiveData.value = ""
        catalogLiveData.value = ""
        imageLiveData.value = null
        isUploadLiveData.value = -1
        isImageUploadLiveData.value = false

    }


    fun onImageUpload(data: Bitmap){
        //val exif = ExifInterface(data)

        val temp = ExifInterface.TAG_GPS_LATITUDE
        Log.d("data", "$temp")
    }

    fun createCopyAndReturnRealPath(context: Context, uri: Uri) :String? {
        val contentResolver = context.contentResolver ?: return null

        val filePath = (context.applicationInfo.dataDir + File.separator
                + System.currentTimeMillis())
        val file = File(filePath)
        try {
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val outputStream: OutputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.d("file", file.absolutePath)

        getGps(file.absolutePath)

        return file.absolutePath
    }

    private fun getGps(absolutePath: String) {
        val exif = ExifInterface(absolutePath)
        var temp: DoubleArray? = exif.latLong
        Log.d("location", "$temp")
        val latitude = exif.latLong?.get(0)
        val longitude = exif.latLong?.get(1)
        Log.d("latitude", "$latitude")
        Log.d("longitude", "$longitude")
    }


}