package com.apparchitecture.app.ui.common

import androidx.lifecycle.ViewModel
import com.apparchitecture.usecases.UseCaseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class BaseUiState {
    object Loading : BaseUiState()
    object Start : BaseUiState()
    data class UseCaseError(val useCaseError: UseCaseResponse.Error<*>) : BaseUiState()
}

abstract class BaseViewModel : ViewModel() {

    private val _baseUiState: MutableStateFlow<BaseUiState> = MutableStateFlow(BaseUiState.Start)
    val baseUiState: StateFlow<BaseUiState>
        get() = _baseUiState

    protected fun <T> processUseCaseResponse(
        useCaseResponse: UseCaseResponse<T>,
        onSuccess: (T) -> Unit
    ) {

        when (useCaseResponse) {

            is UseCaseResponse.Success -> {
                onSuccess(useCaseResponse.data)
            }

            is UseCaseResponse.Error -> {
                _baseUiState.value = BaseUiState.UseCaseError(useCaseResponse)
            }

        }
    }
}