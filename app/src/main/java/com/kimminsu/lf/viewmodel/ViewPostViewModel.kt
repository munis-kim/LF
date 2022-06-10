package com.kimminsu.lf.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kimminsu.lf.PostInfo
import com.kimminsu.lf.data.Post
import com.kimminsu.lf.utils.SingleLiveEvent

class ViewPostViewModel : ViewModel() {
    var titleLiveData = MutableLiveData("")
    var contentLiveData = MutableLiveData("")
    var isModifyLiveData = SingleLiveEvent<Any>()
    var isDeleteLiveData = SingleLiveEvent<Any>()
    fun onModify(){

    }

    fun onDelete(){

    }

    fun loadPost(): Uri {
        titleLiveData.value = PostInfo.title
        contentLiveData.value = PostInfo.content
        return PostInfo.image!!
    }
}