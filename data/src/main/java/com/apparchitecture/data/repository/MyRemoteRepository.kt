package com.apparchitecture.data.repository

import com.apparchitecture.domain.MyEntity
import com.apparchitecture.data.network.MyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MyRemoteRepository {

    suspend fun getCharacters(): MyEntity?

}

class MyRemoteRepositoryImpl @Inject constructor(
    private val myApi: MyApi
) : MyRemoteRepository {

    override suspend fun getCharacters(): MyEntity? {
        return withContext(Dispatchers.IO){
            myApi.getUsers()
        }

    }
}