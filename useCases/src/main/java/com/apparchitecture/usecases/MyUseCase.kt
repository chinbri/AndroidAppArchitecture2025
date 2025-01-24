package com.apparchitecture.usecases

import com.apparchitecture.data.repository.MyModelRepository
import com.apparchitecture.domain.Character
import com.apparchitecture.domain.MyEntity
import javax.inject.Inject

interface MyUseCase : BaseUseCase<String, MyEntity>

class MyUseCaseImpl @Inject constructor(
    private val repository: MyModelRepository
) : MyUseCase {

    override suspend fun execute(request: String): UseCaseResponse<MyEntity> {
        val models = repository.getMyModels()
        return UseCaseResponse.Success(
            MyEntity(
                listOf(
                    Character(
                        if (models.isEmpty()) "empty" else models[0],
                        "test"
                    )
                )
            )
        )
    }

}