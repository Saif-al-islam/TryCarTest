package com.saif.trycartest.domain.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class BaseUseCase {

    protected fun <T> startFlow(
        apiBlock: suspend () -> Resource<T>,
        additionalWorkAfterSuccess: ((T?) -> Unit)? = null
    ) = flow<Resource<T>> {
        emit(Resource.Loading(true))
        val result = apiBlock()
        emit(Resource.Loading(false))
        emit(result)

        if (result is Resource.Success)
            additionalWorkAfterSuccess?.invoke(result.value)

    }.flowOn(Dispatchers.IO)


}