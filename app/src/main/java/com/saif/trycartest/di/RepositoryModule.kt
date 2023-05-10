package com.saif.trycartest.di


import com.saif.trycartest.data.remote.GeneralService
import com.saif.trycartest.data.remote.PostDao
import com.saif.trycartest.data.repo.GeneralRepositoryImpl
import com.saif.trycartest.domain.repo.GeneralRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideGeneralRepository(
        api: GeneralService,
        postDao: PostDao,
    ): GeneralRepository = GeneralRepositoryImpl(api, postDao)


}