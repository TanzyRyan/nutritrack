package com.ryan.nutritrack.data.network

data class FruitResponseModel (
    var name: String,
    var id: String,
    var family: String,
    var order: String,
    var genus: String,
    val nutritions: Map<String, Double>
)
