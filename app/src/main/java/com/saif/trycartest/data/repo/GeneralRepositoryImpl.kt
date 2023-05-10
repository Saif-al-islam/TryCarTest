package com.saif.trycartest.data.repo

import com.saif.trycartest.data.core.BaseRepository
import com.saif.trycartest.data.models.PostDto
import com.saif.trycartest.data.models.mapTo
import com.saif.trycartest.data.remote.GeneralService
import com.saif.trycartest.data.remote.PostDao
import com.saif.trycartest.domain.core.Resource
import com.saif.trycartest.domain.models.Comment
import com.saif.trycartest.domain.models.Post
import com.saif.trycartest.domain.repo.GeneralRepository
import javax.inject.Inject

class GeneralRepositoryImpl @Inject constructor(
    private val service: GeneralService,
    private val postDao: PostDao
) : GeneralRepository, BaseRepository() {

    override suspend fun getPosts(): Resource<List<Post>> {
        return safeApiCall {
            service.getPosts().map { it.mapTo() }
        }
    }

    override suspend fun getPostComments(postId: Int): Resource<List<Comment>> {
        return safeApiCall {
            service.getPostComments(postId).map { it.mapTo() }
        }
    }

    override suspend fun getLocalPosts(): Resource<List<Post>> {
        return Resource.Success(postDao.getAllPosts().map { it.mapTo() })
    }

    override suspend fun getLocalFavoritePosts(): Resource<List<Post>> {
        return Resource.Success(postDao.getAllFavPosts().map { it.mapTo() })
    }

    override suspend fun insertFavPost(post: Post) {
        postDao.insertPosts(PostDto(title = post.title, body = post.body, id = post.id, userId = post.userId, isFav = true))
    }
}