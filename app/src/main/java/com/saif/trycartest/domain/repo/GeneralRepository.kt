package com.saif.trycartest.domain.repo

import com.saif.trycartest.domain.core.Resource
import com.saif.trycartest.domain.models.Comment
import com.saif.trycartest.domain.models.Post

interface GeneralRepository {

    suspend fun getPosts(): Resource<List<Post>>

    suspend fun getPostComments(postId: Int): Resource<List<Comment>>

    suspend fun getLocalPosts(): Resource<List<Post>>

    suspend fun getLocalFavoritePosts(): Resource<List<Post>>

    suspend fun insertFavPost(post: Post)


}