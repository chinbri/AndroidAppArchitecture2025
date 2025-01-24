package com.apparchitecture.usecases

import com.apparchitecture.data.repository.MyModelRepository
import javax.inject.Inject

interface AddUseCase: BaseUseCase<String, Unit>

class AddUseCaseImpl @Inject constructor(
    private val repository: MyModelRepository
): AddUseCase {

    override suspend fun execute(request: String): UseCaseResponse<Unit> {
        repository.add(request)
        return UseCaseResponse.Success(Unit)
    }

}