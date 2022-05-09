package com.kimminsu.lf.viewmodel

import androidx.lifecycle.ViewModel
import com.kimminsu.lf.utils.SingleLiveEvent

class ViewPostViewModel : ViewModel() {
    var isModifyLiveData = SingleLiveEvent<Any>()
    var isDeleteLiveData = SingleLiveEvent<Any>()

    fun onModify(){

    }

    fun onDelete(){

    }

}