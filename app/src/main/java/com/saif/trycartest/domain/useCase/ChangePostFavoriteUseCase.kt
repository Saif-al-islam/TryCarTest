package com.saif.trycartest.domain.useCase

import com.saif.trycartest.domain.core.BaseUseCase
import com.saif.trycartest.domain.models.Post
import com.saif.trycartest.domain.repo.GeneralRepository
import javax.inject.Inject

class ChangePostFavoriteUseCase @Inject constructor(
    private val repository: GeneralRepository
) : BaseUseCase() {

    operator fun invoke(post: Post) = startFlow({
        repository.changePostFavorite(post)
    })

}