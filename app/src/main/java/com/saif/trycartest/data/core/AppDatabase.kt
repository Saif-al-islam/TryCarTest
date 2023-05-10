package com.saif.trycartest.data.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saif.trycartest.data.models.CommentDto
import com.saif.trycartest.data.models.PostDto
import com.saif.trycartest.data.remote.PostDao

@Database(entities = [PostDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}