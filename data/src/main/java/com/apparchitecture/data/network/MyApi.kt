package com.apparchitecture.data.network

import com.apparchitecture.domain.MyEntity
import retrofit2.http.GET

interface MyApi {

    @GET("character")
    suspend fun getUsers(): MyEntity

}