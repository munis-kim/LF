package com.kimminsu.lf.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kimminsu.lf.MainActivity
import com.kimminsu.lf.R
import com.kimminsu.lf.databinding.FragmentMyprofileBinding
import com.kimminsu.lf.databinding.FragmentUploadBinding
import com.kimminsu.lf.viewmodel.UploadViewModel

class UploadFragment : Fragment() {

    private val uploadViewModel: UploadViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val uploadBinding: FragmentUploadBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_upload, container, false)
        val mainActivity = activity as MainActivity
        uploadBinding.uploadViewModel = uploadViewModel
        mainActivity.hideBar(true, true)
        
        return uploadBinding.root
    }

}