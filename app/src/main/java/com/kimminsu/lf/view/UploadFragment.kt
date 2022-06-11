package com.kimminsu.lf.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kimminsu.lf.MainActivity
import com.kimminsu.lf.R
import com.kimminsu.lf.databinding.FragmentUploadBinding
import com.kimminsu.lf.viewmodel.UploadViewModel
import java.io.IOException

class UploadFragment : Fragment() {

    private val uploadViewModel: UploadViewModel by viewModels()
    lateinit var uploadBinding: FragmentUploadBinding
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        uploadBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_upload, container, false)
        val mainActivity = activity as MainActivity
        uploadBinding.uploadViewModel = uploadViewModel
        mainActivity.hideBar(true, true)
        uploadViewModel.clearLiveData()

        val isUploadObserver = Observer<Int>{
            if(it == 0){
                Toast.makeText(context, "등록 성공", Toast.LENGTH_SHORT).show()
                mainActivity.onFragmentChange(3)
                uploadViewModel.clearLiveData()
            } else if(it in 1..4)
                showErrorMessage(it)
        }
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val imageBitmap: Bitmap = result.data!!.extras?.get("data") as Bitmap
                    val uri = uploadViewModel.getImageUri(context, imageBitmap)
                    uploadViewModel.setImage(uri)
                    uploadBinding.image.setImageBitmap(imageBitmap)
                }
            }

        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    if(result.data != null){
                        val uri = result.data!!.data as Uri
                        var bitmap: Bitmap? = null
                        try{
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                                val source: ImageDecoder.Source? =
                                    context?.let { ImageDecoder.createSource(it.contentResolver, uri) }
                                bitmap = source?.let { ImageDecoder.decodeBitmap(it) }
                            } else{
                                bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                            }
                        } catch(e:IOException){
                            e.printStackTrace()
                        }
                        uploadViewModel.setImage(uri)
                        uploadBinding.image.setImageBitmap(bitmap)
                    }
                }
            }

        uploadViewModel.isCameraLiveData.observe(viewLifecycleOwner) {
            onCamera()
        }

        uploadViewModel.isGalleryLiveData.observe(viewLifecycleOwner) {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            galleryLauncher.launch(galleryIntent)
        }

        uploadViewModel.isUploadLiveData.observe(viewLifecycleOwner, isUploadObserver)
        return uploadBinding.root
    }

    private fun showErrorMessage(errorCode: Int) {
        val errorMessage: String = when (errorCode){
            1 -> "제목을 입력해주세요."
            2 -> "내용을 입력해주세요."
            3 -> "사진을 입력해주세요."
            else -> "에러가 발생했습니다."
        }
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun onCamera(){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(cameraIntent)
    }



}