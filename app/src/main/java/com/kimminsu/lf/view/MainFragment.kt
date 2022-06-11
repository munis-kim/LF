package com.kimminsu.lf.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kimminsu.lf.MainActivity
import com.kimminsu.lf.R
import com.kimminsu.lf.data.Post
import com.kimminsu.lf.databinding.FragmentMainBinding
import com.kimminsu.lf.viewmodel.MainViewModel
import com.kimminsu.lf.viewmodel.MyProfileViewModel

class MainFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()
    private val myProfileViewModel: MyProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainBinding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false )
        val mainActivity = activity as MainActivity
        mainActivity.hideBar(false, false)
        mainBinding.mainViewModel = mainViewModel

        mainViewModel.loadPost()

        var postList = ArrayList<Post>()

        mainViewModel.isGoUploadLiveData.observe(viewLifecycleOwner) {
            mainActivity.onFragmentChange(7)
        }

        mainViewModel.isLoadLiveData.observe(viewLifecycleOwner){
            postList = mainViewModel.postList
            val recyclerView = mainBinding.postRecyclerView
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(context)

            val adapter = PostAdapter(mainViewModel, myProfileViewModel, postList, 0)
            mainBinding.postRecyclerView.adapter = adapter
        }

        mainViewModel.isPostClickLiveData.observe(viewLifecycleOwner){
            mainActivity.onFragmentChange(8)
        }


        return mainBinding.root
    }

    fun search(searchWord: String?){
        mainViewModel.onSearch(searchWord)
    }


}