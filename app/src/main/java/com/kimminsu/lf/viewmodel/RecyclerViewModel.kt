package com.kimminsu.lf.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kimminsu.lf.utils.SingleLiveEvent

class RecyclerViewModel(private val mode: Int) : ViewModel() {

    // 데이터 로드 실패시 에러 이벤트
    private val _navigateToLoadFail = SingleLiveEvent<Any>()
    val navigateToLoadFail: LiveData<Any>
        get() = _navigateToLoadFail

    private lateinit var list: List<Any?>

    private val _dataList = MutableLiveData<List<Any?>>()
    val dataList: LiveData<List<Any?>> = _dataList

    companion object{
        const val requestAllPostList = 0
        const val requestMyPostList = 1
        const val requestUpImage = 2
    }

    init{
        when(mode){
            requestAllPostList -> {} // 메인 화면 나중에 구현할 것
            requestMyPostList -> {} // 내 프로필에서 글 나중에 구현
            requestUpImage -> {

            }
        }
    }

}