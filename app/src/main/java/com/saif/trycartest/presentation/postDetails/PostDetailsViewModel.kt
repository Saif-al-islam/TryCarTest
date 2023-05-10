package com.saif.trycartest.presentation.postDetails

import androidx.lifecycle.viewModelScope
import com.saif.trycartest.domain.models.Comment
import com.saif.trycartest.domain.models.Post
import com.saif.trycartest.domain.useCase.ChangePostFavoriteUseCase
import com.saif.trycartest.domain.useCase.GetLocalPostsUseCase
import com.saif.trycartest.domain.useCase.GetPostCommentsUseCase
import com.saif.trycartest.domain.useCase.IsPostFavoriteUseCase
import com.saif.trycartest.presentation.core.BaseViewModel
import com.saif.trycartest.presentation.core.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val GetPostCommentsUseCase: GetPostCommentsUseCase,
    private val IsPostFavoriteUseCase: IsPostFavoriteUseCase,
    private val ChangePostFavoriteUseCase: ChangePostFavoriteUseCase,

    ) : BaseViewModel() {


    private val _postsFlow = MutableStateFlow(UiEvent())
    val postsFlow = _postsFlow.asStateFlow()

    private val _isPostFavFlow = MutableStateFlow(UiEvent())
    val isPostFavFlow = _isPostFavFlow.asStateFlow()

    fun getPostComments(postId: Int) {
        GetPostCommentsUseCase(postId).onEach {
            it.checkResponse(_postsFlow) {
                _postsFlow.emit(SuccessCommentsData(it))
            }
        }
            .launchIn(viewModelScope)
    }

    fun isPostFavoriteUseCase(postId: Int) {
        IsPostFavoriteUseCase(postId).onEach {
            it.checkResponse(_isPostFavFlow) {
                _isPostFavFlow.emit(SuccessPostFavData(it ?: 0))
            }
        }.launchIn(viewModelScope)
    }

    fun changePostFavorite(post: Post) {
        ChangePostFavoriteUseCase(post).onEach {
            _isPostFavFlow.emit(SuccessPostFavData(post.isFav))
        }.launchIn(viewModelScope)
    }


}

data class SuccessCommentsData(val data: List<Comment>?) : UiEvent()
data class SuccessPostFavData(val isFav: Int) : UiEvent()
