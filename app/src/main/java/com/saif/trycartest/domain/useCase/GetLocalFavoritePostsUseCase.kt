package com.saif.trycartest.domain.useCase

import com.saif.trycartest.domain.core.BaseUseCase
import com.saif.trycartest.domain.repo.GeneralRepository
import javax.inject.Inject

class GetLocalFavoritePostsUseCase @Inject constructor(
    private val repository: GeneralRepository
) : BaseUseCase()  {

    operator fun invoke() = startFlow({
        repository.getLocalFavoritePosts()
    })


}