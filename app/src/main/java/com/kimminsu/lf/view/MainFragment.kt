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
import com.kimminsu.lf.databinding.FragmentMainBinding
import com.kimminsu.lf.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainBinding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false )
        val mainActivity = activity as MainActivity
        mainActivity.hideBar(false, false)
        mainBinding.mainViewModel = mainViewModel

        mainViewModel.isGoUploadLiveData.observe(viewLifecycleOwner) {
            mainActivity.onFragmentChange(7)
        }


        return mainBinding.root
    }


}