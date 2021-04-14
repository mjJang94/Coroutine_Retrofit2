package com.mj.coroutine_retrofit2.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {

    private fun getRetrofit(): Retrofit{

        val baseUrl = "https://mjjang94.github.io"

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService(): APIService{
        return getRetrofit().create(APIService::class.java)
    }
}