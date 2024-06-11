package com.example.practiceflows.ui.theme

data class LoginRepository (val name: String="Angles Panchules" , val psw: String = "1243"){
}

sealed class UseCaseFlowResult<out T> {
    data class Succeed<out T>(val data: T) : UseCaseFlowResult<T>()
    data class Failed(val error: Throwable) : UseCaseFlowResult<Nothing>()
    object Loading : UseCaseFlowResult<Nothing>()
}