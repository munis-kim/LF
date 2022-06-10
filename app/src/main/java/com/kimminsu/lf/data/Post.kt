package com.kimminsu.lf.data

import android.net.Uri

data class Post(
    var nickname: String?,
    val title: String?,
    val content: String?,
    val catalog: String?,
    val uploadTime: String?,
    val image: Uri?
    )
