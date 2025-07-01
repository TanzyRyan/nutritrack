package com.ryan.nutritrack.data.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface FruitAPIService {
    @GET("{fruit}")
    suspend fun getFruit(@Path("fruit") fruit: String): Response<FruitResponseModel>

    companion object {

        var BASE_URL = "https://www.fruityvice.com/api/fruit/"

        fun create(): FruitAPIService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(FruitAPIService::class.java)

        }
    }
}