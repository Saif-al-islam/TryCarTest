package com.saif.trycartest.presentation.postDetails

import androidx.lifecycle.viewModelScope
import com.saif.trycartest.domain.models.Comment
import com.saif.trycartest.domain.models.Post
import com.saif.trycartest.domain.useCase.GetLocalPostsUseCase
import com.saif.trycartest.domain.useCase.GetPostCommentsUseCase
import com.saif.trycartest.presentation.core.BaseViewModel
import com.saif.trycartest.presentation.core.UiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(
    private val GetPostCommentsUseCase: GetPostCommentsUseCase
) : BaseViewModel() {


    private val _postsFlow = MutableStateFlow(UiEvent())
    val postsFlow = _postsFlow.asStateFlow()

    fun getPostComments(postId: Int) {
        GetPostCommentsUseCase(postId).onEach {
            it.checkResponse(_postsFlow) {
                _postsFlow.emit(SuccessCommentsData(it))
            }
        }
            .launchIn(viewModelScope)
    }


}

data class SuccessCommentsData(val data: List<Comment>?) : UiEvent()
