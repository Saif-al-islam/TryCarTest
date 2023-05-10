package com.saif.trycartest.presentation.favoritePosts

import androidx.lifecycle.viewModelScope
import com.saif.trycartest.domain.models.Post
import com.saif.trycartest.domain.useCase.GetLocalFavoritePostsUseCase
import com.saif.trycartest.presentation.core.BaseViewModel
import com.saif.trycartest.presentation.core.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoritePostsViewModel @Inject constructor(
    private val GetLocalFavoritePostsUseCase: GetLocalFavoritePostsUseCase
) : BaseViewModel() {


    private val _postsFlow = MutableStateFlow(UiEvent())
    val postsFlow = _postsFlow.asStateFlow()

    fun getFavoritePosts() {
        GetLocalFavoritePostsUseCase().onEach {
            it.checkResponse(_postsFlow) {
                _postsFlow.emit(SuccessFavPostsData(it))
            }
        }
            .launchIn(viewModelScope)
    }


}

data class SuccessFavPostsData(val data: List<Post>?) : UiEvent()
