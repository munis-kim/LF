package com.kimminsu.lf.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kimminsu.lf.MainActivity
import com.kimminsu.lf.R
import com.kimminsu.lf.data.Post
import com.kimminsu.lf.databinding.FragmentMyprofileBinding
import com.kimminsu.lf.viewmodel.MainViewModel
import com.kimminsu.lf.viewmodel.MyProfileViewModel

class MyProfileFragment : Fragment() {

    private val myprofileViewModel: MyProfileViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myprofileBinding: FragmentMyprofileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_myprofile, container, false)
        val mainActivity = activity as MainActivity

        myprofileBinding.myprofileViewModel = myprofileViewModel
        mainActivity.hideBar(false, true)
        myprofileViewModel.loadPost()

        var postList = ArrayList<Post>()
        myprofileViewModel.isLogoutLiveData.observe(viewLifecycleOwner){
            mainActivity.onFragmentChange(1)
        }

        myprofileViewModel.isLoadLiveData.observe(viewLifecycleOwner){
            postList = myprofileViewModel.postList
            val recyclerView = myprofileBinding.postRecyclerView
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(context)

            val adapter = PostAdapter(mainViewModel, myprofileViewModel, postList, 1)
            myprofileBinding.postRecyclerView.adapter = adapter
        }

        myprofileViewModel.isPostClickLiveData.observe(viewLifecycleOwner){
            mainActivity.onFragmentChange(8)
        }


        return myprofileBinding.root
    }
}