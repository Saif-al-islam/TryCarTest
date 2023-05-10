package com.saif.trycartest.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Post(
     val id: Int?,
    val body: String?,
    val title: String?,
    val userId: Int?,
     val isFav: Int = 0
): Parcelable