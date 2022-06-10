package com.kimminsu.lf.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kimminsu.lf.PostInfo
import com.kimminsu.lf.UserInfo
import com.kimminsu.lf.data.Post
import com.kimminsu.lf.repository.AuthRepository
import com.kimminsu.lf.repository.PostRepository
import com.kimminsu.lf.utils.SingleLiveEvent

class MyProfileViewModel : ViewModel() {
    private var postRepository = PostRepository.getInstance()
    var userId = UserInfo.userId
    var isLogoutLiveData = SingleLiveEvent<Any>()
    var isLoadLiveData = SingleLiveEvent<Any>()
    var isPostClickLiveData = SingleLiveEvent<Any>()
    var postList = ArrayList<Post>()

    val GoPost: LiveData<Any>
        get() = isPostClickLiveData

    val GoLogout: LiveData<Any>
        get() = isLogoutLiveData

    val GoLoad: LiveData<Any>
    get() = isLoadLiveData

    fun onLogout() {
        Firebase.auth.signOut()
        isLogoutLiveData.call()
    }

    fun loadPost(){
        postRepository.loadMyPost(UserInfo.userId!!) { result ->
            postList = result
            isLoadLiveData.call()
        }
    }

    fun clickPost(post: Post) {
        PostInfo.nickname = post.nickname
        PostInfo.title = post.title
        PostInfo.content = post.content
        PostInfo.catalog = post.catalog
        PostInfo.uploadTime = post.uploadTime
        PostInfo.image = post.image
        isPostClickLiveData.call()
    }
}