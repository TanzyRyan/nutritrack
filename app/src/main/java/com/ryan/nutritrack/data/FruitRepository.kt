package com.ryan.nutritrack.data

import com.ryan.nutritrack.data.network.FruitAPIService
import com.ryan.nutritrack.data.network.FruitResponseModel

class FruitRepository {
    private val apiService = FruitAPIService.create()

    suspend fun getFruit(fruit: String): FruitResponseModel? {
        val response = apiService.getFruit(fruit)

        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}