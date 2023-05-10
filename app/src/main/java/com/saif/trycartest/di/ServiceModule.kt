package com.saif.trycartest.di

import com.saif.trycartest.data.core.AppDatabase
import com.saif.trycartest.data.remote.GeneralService
import com.saif.trycartest.data.remote.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun provideGeneralService(
        retrofit: Retrofit
    ): GeneralService = retrofit.create(GeneralService::class.java)

    @Provides
    @Singleton
    fun providePostDBService(
        db: AppDatabase
    ): PostDao = db.postDao()


}