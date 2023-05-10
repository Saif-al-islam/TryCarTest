package com.saif.trycartest.presentation.core

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saif.trycartest.R
import com.saif.trycartest.domain.core.FailureStatus
import com.saif.trycartest.domain.core.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


open class BaseViewModel : ViewModel() {

    val launchAuthLiveData = MutableLiveData(false)

    private val _loadingFlow by lazy { MutableStateFlow<Boolean>(false) }
    val loadingFlow by lazy { _loadingFlow.asStateFlow() }



    protected suspend fun <T> Resource<T>.checkResponse(
        uiEvent: MutableSharedFlow<UiEvent>,
        loadingState: MutableSharedFlow<Boolean> = _loadingFlow,
        success: suspend (T?) -> Unit
    ) {
        Log.d("saif", "checkResponse: $this")
        when (this) {
            is Resource.Failure -> {
                handleFailedResource(this, uiEvent)
            }
            is Resource.Success -> {
                success(this.value)
            }
            is Resource.Loading ->
                loadingState.emit(this.loadingStatus)
            else -> {}
        }
    }

    private suspend fun handleFailedResource(
        resource: Resource.Failure,
        uiEventFlowFailure: MutableSharedFlow<UiEvent>
    ) {
        val error: Error =
            when (resource.status) {
                FailureStatus.API_FAIL -> Error.ErrorStr(resource.message ?: "")
                FailureStatus.UNAUTHENTICATED -> Error.ErrorInt(R.string.user_not_logged_in_msg)
                FailureStatus.NO_INTERNET -> Error.ErrorInt(R.string.no_internet_connection)
                FailureStatus.OTHER -> Error.ErrorInt(R.string.try_again_something_went_wrong)
            }

        uiEventFlowFailure.emit(UiEvent.ShowMessage(error))
    }


}

open class UiEvent {
    class ShowMessage(val message: Error) : UiEvent()
}

