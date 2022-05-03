package com.kimminsu.lf.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kimminsu.lf.MainActivity

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val mainActivity = activity as MainActivity
        mainActivity.hideBar(false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}