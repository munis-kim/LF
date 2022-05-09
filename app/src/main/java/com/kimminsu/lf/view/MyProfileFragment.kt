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
import com.kimminsu.lf.viewmodel.MyProfileViewModel

class MyProfileFragment : Fragment() {

    private val myprofileViewModel: MyProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myprofileBinding: FragmentMyprofileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_myprofile, container, false)
        val mainActivity = activity as MainActivity
        myprofileBinding.myprofileViewModel = myprofileViewModel
        mainActivity.hideBar(false, true)

        return myprofileBinding.root
    }
}