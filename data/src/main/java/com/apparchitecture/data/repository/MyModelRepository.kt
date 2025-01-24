package com.apparchitecture.data.repository

import com.apparchitecture.data.database.MyModel
import com.apparchitecture.data.database.MyModelDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MyModelRepository {
    suspend fun getMyModels(): List<String>

    suspend fun add(name: String)
}

class DefaultMyModelRepository @Inject constructor(
    private val myModelDao: MyModelDao
) : MyModelRepository {

    override suspend fun getMyModels(): List<String> = withContext(Dispatchers.IO){
        myModelDao.getMyModels().map { items -> items.name }
    }

    override suspend fun add(name: String) = withContext(Dispatchers.IO){
        myModelDao.insertMyModel(MyModel(name = name))
    }

}
