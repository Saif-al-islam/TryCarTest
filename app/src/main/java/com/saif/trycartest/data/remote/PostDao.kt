package com.saif.trycartest.data.remote

import androidx.room.Insert
import androidx.room.Query
import com.saif.trycartest.data.models.PostDto

interface PostDao {

    @Query("SELECT * FROM post")
    suspend fun getAllPosts(): List<PostDto>

    @Query("SELECT * FROM post where isFav=true")
    suspend fun getAllFavPosts(): List<PostDto>

    @Insert
    suspend fun insertPosts(vararg users: PostDto)

}