package com.saif.trycartest.data.models


import com.google.gson.annotations.SerializedName
import com.saif.trycartest.domain.models.Comment

data class CommentDto(
    @SerializedName("body")
    val body: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("postId")
    val postId: Int?
)

fun CommentDto.mapTo() = Comment(
    body = body,
    email = email,
    id = id,
    name = name,
    postId = postId
)
