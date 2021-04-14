package com.mj.coroutine_retrofit2.api

import com.mj.coroutine_retrofit2.response.UserResponse
import retrofit2.http.GET

interface APIService {

    @GET("/HostingRepository/mvvm_sample_data.json")
    suspend fun getUserData() : UserResponse
}