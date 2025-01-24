package com.apparchitecture.usecases

import com.apparchitecture.data.repository.MyRemoteRepository
import com.apparchitecture.domain.MyEntity
import javax.inject.Inject

interface MyGetCharactersUseCase : BaseUseCase<String, MyEntity?>

class MyGetCharactersUseCaseImpl @Inject constructor(
    private val remoteRepository: MyRemoteRepository
) : MyGetCharactersUseCase {

    override suspend fun execute(request: String): UseCaseResponse<MyEntity?> {
        return UseCaseResponse.Success(
            remoteRepository.getCharacters()
        )
    }

}