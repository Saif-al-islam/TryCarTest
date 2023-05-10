package com.saif.trycartest.presentation.posts

import androidx.lifecycle.viewModelScope
import com.saif.trycartest.domain.models.Post
import com.saif.trycartest.domain.useCase.GetLocalPostsUseCase
import com.saif.trycartest.domain.useCase.GetPostsUseCase
import com.saif.trycartest.presentation.core.BaseViewModel
import com.saif.trycartest.presentation.core.UiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val GetPostsUseCase: GetPostsUseCase,
    private val GetLocalPostsUseCase: GetLocalPostsUseCase
) : BaseViewModel() {


    private val _postsFlow = MutableStateFlow(UiEvent())
    val postsFlow = _postsFlow.asStateFlow()

    fun getPosts(isConnected: Boolean) {
        if (isConnected) {
            GetPostsUseCase()
                .onEach {
                    it.checkResponse(_postsFlow) {
                        _postsFlow.emit(SuccessPostsData(it))
                    }
                }
                .launchIn(viewModelScope)
        } else {
            GetLocalPostsUseCase().onEach {
                it.checkResponse(_postsFlow) {
                    _postsFlow.emit(SuccessPostsData(it))
                }
            }
                .launchIn(viewModelScope)
        }
    }


}

data class SuccessPostsData(val data: List<Post>?) : UiEvent()
