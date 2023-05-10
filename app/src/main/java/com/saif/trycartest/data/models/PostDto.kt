package com.saif.trycartest.data.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.saif.trycartest.domain.models.Post

@Entity
data class PostDto(
    @SerializedName("body")
    val body: String?,
    @PrimaryKey
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("userId")
    val userId: Int?,
    val isFav: Boolean = false
)

fun PostDto.mapTo() = Post(body = body, id = id, title = title, userId = userId)