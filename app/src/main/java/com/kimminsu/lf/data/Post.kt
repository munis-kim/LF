package com.kimminsu.lf.data

import android.net.Uri

data class Post(
    val nickname: String?,
    val title: String?,
    val content: String?,
    val catalog: String?,
    val uploadTime: String?,
    val image: Uri?
    )
