package com.apparchitecture.usecases

sealed class UseCaseResponse<Response> {
    data class Success<Response>(val data: Response) : UseCaseResponse<Response>()
    data class Error<Response>(
        val type: UseCaseErrorType = UseCaseErrorType.ERROR,
        val throwable: Throwable? = null,
        val errorMessage: String? = throwable?.message
    ) : UseCaseResponse<Response>()

}

enum class UseCaseErrorType {
    ERROR,
    WARNING,
    OTHER
}