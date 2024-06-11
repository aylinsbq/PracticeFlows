package com.example.practiceflows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practiceflows.ui.theme.LoginRepository
import com.example.practiceflows.ui.theme.UseCaseFlowResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MAinViewModel:ViewModel() {
    private val _result = MutableStateFlow<UseCaseFlowResult<Any>>(UseCaseFlowResult.Loading)
    val result: StateFlow<UseCaseFlowResult<Any>> = _result.asStateFlow()

    private val firstUseCase: Flow<UseCaseFlowResult<LoginRepository>> = flow {
        emit(UseCaseFlowResult.Loading)
        delay(1000)
        emit(UseCaseFlowResult.Succeed(LoginRepository()))
    }
    private val secondUseCase: Flow<UseCaseFlowResult<List<String>>> = flow {
        emit(UseCaseFlowResult.Loading)
        kotlinx.coroutines.delay(1000)
        emit(UseCaseFlowResult.Succeed(listOf("Post1", "Post2", "Post3")))
    }
    fun executeUseCases() {
        viewModelScope.launch {
            firstUseCase.flatMapConcat { firstResult ->
                when (firstResult) {
                    is UseCaseFlowResult.Failed -> {
                        flow { emit(UseCaseFlowResult.Failed(firstResult.error)) }
                    }
                    is UseCaseFlowResult.Loading -> {
                        flow { emit(UseCaseFlowResult.Loading) }
                    }
                    is UseCaseFlowResult.Succeed -> {
                        secondUseCase
                        flow { emit(UseCaseFlowResult.Succeed(firstResult.data)) }
                        //secondUseCase
                    }
                }
            }.collect { result ->
                _result.value = result
            }
        }
    }
}