package com.apparchitecture.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

interface BaseUseCase<Request, Response> {
    suspend operator fun invoke(request: Request): UseCaseResponse<Response> {

        return try{
            withContext(Dispatchers.Default){
                execute(request)
            }
        }catch (e: Exception){

            UseCaseResponse.Error(
                type = UseCaseErrorType.ERROR,
                e,
                e.message
            )
        }

    }

    suspend fun execute(request: Request): UseCaseResponse<Response>

}