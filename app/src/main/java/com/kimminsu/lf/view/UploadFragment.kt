package com.kimminsu.lf.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kimminsu.lf.MainActivity
import com.kimminsu.lf.R
import com.kimminsu.lf.databinding.FragmentUploadBinding
import com.kimminsu.lf.viewmodel.RecyclerViewModel
import com.kimminsu.lf.viewmodel.UploadViewModel

class UploadFragment : Fragment() {

    private val uploadViewModel: UploadViewModel by viewModels()
    //lateinit var viewModel: RecyclerViewModel
    lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private var imageAdapter: MultiImageAdapter? = MultiImageAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val uploadBinding: FragmentUploadBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_upload, container, false)
        val mainActivity = activity as MainActivity
        uploadBinding.uploadViewModel = uploadViewModel
        mainActivity.hideBar(true, true)

        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val bitmap = result?.data?.extras?.get("data") as Bitmap
                    uploadViewModel.setImage(bitmap)
                }
            }

        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    if(result.data != null){
                        uploadViewModel.onImageUpload(result.data!!.data as Uri)
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

        uploadViewModel.imageLiveData.observe(viewLifecycleOwner, Observer{imageAdapter?.setDataList(it)})
        return uploadBinding.root
    }

    private fun showErrorMessage() {
        Toast.makeText(context, "데이터 로드 실패", Toast.LENGTH_SHORT).show()
    }

    private fun onImageUpload() {

    }

    private fun onCamera(){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(cameraIntent)
    }

}