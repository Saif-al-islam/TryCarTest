package com.saif.trycartest.domain.useCase

import com.saif.trycartest.domain.core.BaseUseCase
import com.saif.trycartest.domain.models.Post
import com.saif.trycartest.domain.repo.GeneralRepository
import javax.inject.Inject

class InsertNewPostsUseCase @Inject constructor(
    private val repository: GeneralRepository
) : BaseUseCase() {

    operator fun invoke(vararg posts: Post) = startFlow({
        repository.insertNewPosts(*posts)
    })

}