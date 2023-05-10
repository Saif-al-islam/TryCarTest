package com.saif.trycartest.data.repo

import android.util.Log
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

    // dont change the favorite ones
    override suspend fun insertNewPosts(vararg posts: Post): Resource<Unit> {
        val favList = postDao.getAllFavPosts()
        val newPosts = posts.dropWhile { post -> favList.find { it.id == post.id } != null }

        val ids = postDao.insertPosts(
            *newPosts.map { post ->
                PostDto(
                    title = post.title,
                    body = post.body,
                    id = post.id,
                    userId = post.userId,
                    isFav = post.isFav
                )
            }.toTypedArray()
        )

        return Resource.Success(Unit)
    }

    override suspend fun changePostFavorite(post: Post): Resource<Unit> {
        postDao.insertPosts(
            PostDto(
                title = post.title,
                body = post.body,
                id = post.id,
                userId = post.userId,
                isFav = post.isFav
            )
        )

        return Resource.Success(Unit)
    }

    override suspend fun isPostFavorite(postId: Int): Resource<Int> {
        val isF = postDao.isPostFavorite(postId)
        Log.d("saif", "isPostFavorite: isF=${isF.isFav}")
        return Resource.Success(isF.isFav)
    }
}