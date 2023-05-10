package com.saif.trycartest.data.remote

import com.saif.trycartest.data.models.CommentDto
import com.saif.trycartest.data.models.PostDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GeneralService {

    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    @GET("posts/{postId}}/comments")
    suspend fun getPostComments(@Path("postId") postId: Int): List<CommentDto>

}