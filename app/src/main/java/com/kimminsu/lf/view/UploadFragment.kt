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
    lateinit var uploadBinding: FragmentUploadBinding
    lateinit var viewModel: RecyclerViewModel
    lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        uploadBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_upload, container, false)
        val mainActivity = activity as MainActivity
        viewModel = RecyclerViewModel(RecyclerViewModel.requestUpImage)
        uploadBinding.uploadViewModel = uploadViewModel
        mainActivity.hideBar(true, true)

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data!!.extras
                    uploadViewModel.setImage()
                }
            }

        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    if(result.data != null){
                        Log.d("data", "${result.data!!.data as Uri}")
                    }
                    uploadViewModel.onImageUpload(result.data!!.data as Uri)
                    //onImageUpload(result.data!!.data as Uri)
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
        return uploadBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initImageList()

        viewModel.navigateToLoadFail.observe(viewLifecycleOwner, Observer {
            run {
                showErrorMessage(1)
            }
        })
    }

    private fun initImageList() {
        val imageListView: RecyclerView = uploadBinding.multiImage
        val imageAdapter = MultiImageAdapter()
        imageListView.adapter = imageAdapter
        imageListView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.dataList.observe(viewLifecycleOwner, Observer {
            val dataList = it.filterIsInstance<Uri>()
            Log.d("hoxy", "$dataList")
            imageAdapter.setDataList(dataList)
        })
    }

    private fun showErrorMessage(errorCode: Int) {
        val errorMessage: String = when (errorCode){
            1 -> "데이터 로드 실패"
            else -> "에러가 발생했습니다."
        }
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun onCamera(){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(cameraIntent)
    }

    private fun handleCameraImage(intent: Intent?) {
        val bitmap = intent?.extras?.get("data") as Bitmap


    }
}