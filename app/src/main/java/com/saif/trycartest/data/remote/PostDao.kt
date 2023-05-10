package com.saif.trycartest.data.remote

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saif.trycartest.data.models.PostDto


@Dao
interface PostDao {

    @Query("SELECT * FROM postdto")
    suspend fun getAllPosts(): List<PostDto>

    @Query("SELECT * FROM postdto where isFav=1")
    suspend fun getAllFavPosts(): List<PostDto>

    @Query("SELECT * FROM postdto where id=:postId")
    suspend fun isPostFavorite(postId: Int): PostDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(vararg posts: PostDto): Array<Long>


}