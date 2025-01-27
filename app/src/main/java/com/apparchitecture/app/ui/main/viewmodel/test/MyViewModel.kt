package com.apparchitecture.app.ui.main.viewmodel.test

import androidx.lifecycle.viewModelScope
import com.apparchitecture.usecases.AddUseCase
import com.apparchitecture.domain.MyEntity
import com.apparchitecture.usecases.MyGetCharactersUseCase
import com.apparchitecture.usecases.MyUseCase
import com.apparchitecture.app.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MyUiState {
    data object Initial : MyUiState()
    data object Loading : MyUiState()
    data class Error(val throwable: Throwable) : MyUiState()
    data class Success(val data: MyEntity) : MyUiState()
    data object SuccessInsert: MyUiState()

}

@HiltViewModel
class MyViewModel @Inject constructor(
    private val myUseCase: MyUseCase,
    private val addUseCase: AddUseCase,
    private val myGetCharactersUseCase: MyGetCharactersUseCase
) : BaseViewModel() {

    private var _uiState: MutableStateFlow<MyUiState> = MutableStateFlow(MyUiState.Initial)
    val uiState: StateFlow<MyUiState>
        get() = _uiState

    fun test() {

        _uiState.update {
            MyUiState.Loading
        }
        viewModelScope.launch {

            processUseCaseResponse(
                myUseCase("prueba")
            ){ response ->
                _uiState.update {
                    MyUiState.Success(response)
                }

                println(response)
            }

//            processUseCaseResponse(
//                myGetCharactersUseCase("test")
//            ){ charactersResponse ->
//
//                charactersResponse?.results?.forEach {
//                    println(it.name)
//                }
//
//            }

        }
    }

    fun onAddClicked() {
        viewModelScope.launch {
            addUseCase("prueba ${System.currentTimeMillis()}")
            _uiState.update {
                MyUiState.SuccessInsert
            }
        }
    }
}
