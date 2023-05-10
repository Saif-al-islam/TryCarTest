package com.saif.trycartest.data.core

import android.util.Log
import com.saif.trycartest.domain.core.FailureStatus
import com.saif.trycartest.domain.core.Resource
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

open class BaseRepository {

    protected suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        try {
            Log.d("saif", "safeApiCall: start")
            val apiResponse = apiCall.invoke()

            return Resource.Success(apiResponse)

        } catch (throwable: Throwable) {
            Log.d("saif", "safeApiCall: start catch")
            when (throwable) {
                is HttpException -> {
                    return when (throwable.code()) {
                        401 -> {
                            Resource.Failure(FailureStatus.UNAUTHENTICATED, throwable.code(), null)
                        }
                        else -> {
                            return Resource.Failure(
                                FailureStatus.API_FAIL,
                                throwable.code(),
                                throwable.message()
                            )
                        }
                    }

                }

                is UnknownHostException -> {
                    Log.d("saif", "safeApiCall: UnknownHostException")
                    return Resource.Failure(FailureStatus.NO_INTERNET)
                }

                is ConnectException -> {
                    Log.d("saif", "safeApiCall: ConnectException")

                    return Resource.Failure(FailureStatus.NO_INTERNET)
                }
                else -> {
                    Log.d("saif", "safeApiCall: else Exception ${throwable}")
                    return Resource.Failure(FailureStatus.OTHER)
                }
            }
        }
    }
}