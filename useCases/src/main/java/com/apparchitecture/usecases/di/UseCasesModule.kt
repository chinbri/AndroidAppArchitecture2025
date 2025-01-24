
package com.apparchitecture.usecases.di

import com.apparchitecture.usecases.AddUseCase
import com.apparchitecture.usecases.AddUseCaseImpl
import com.apparchitecture.usecases.MyGetCharactersUseCase
import com.apparchitecture.usecases.MyGetCharactersUseCaseImpl
import com.apparchitecture.usecases.MyUseCase
import com.apparchitecture.usecases.MyUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    fun bindsMyUseCase(
        useCase: MyUseCaseImpl
    ): MyUseCase = useCase

    @Provides
    fun bindsAddUseCase(
        useCase: AddUseCaseImpl
    ): AddUseCase = useCase

    @Provides
    fun bindsMyGetCharactersUseCase(
        useCase: MyGetCharactersUseCaseImpl
    ): MyGetCharactersUseCase = useCase



}

