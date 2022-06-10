package com.kimminsu.lf.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.kimminsu.lf.MainActivity
import com.kimminsu.lf.R
import com.kimminsu.lf.data.Post
import com.kimminsu.lf.databinding.FragmentViewpostBinding
import com.kimminsu.lf.viewmodel.ViewPostViewModel

class ViewPostFragment : Fragment() {

    private val viewpostViewModel: ViewPostViewModel by viewModels()
    lateinit var viewpostBinding: FragmentViewpostBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewpostBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_viewpost, container, false)
        val mainActivity = activity as MainActivity
        mainActivity.hideBar(false, false)
        viewpostBinding.viewpostViewModel = viewpostViewModel
        val image = viewpostViewModel.loadPost()
        Glide.with(viewpostBinding.postImage)
            .load(image)
            .into(viewpostBinding.postImage)

        return viewpostBinding.root
    }

}