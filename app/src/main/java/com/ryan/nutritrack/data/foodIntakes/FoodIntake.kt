package com.ryan.nutritrack.data.foodIntakes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FoodIntakes")
data class FoodIntake(

    @PrimaryKey
    val patientId: Int,

    val vegetables: Boolean,
    val fruits: Boolean,
    val redMeat: Boolean,
    val fish: Boolean,
    val grains: Boolean,
    val seafood: Boolean,
    val poultry: Boolean,
    val eggs: Boolean,
    val nutSeeds: Boolean,

    val persona: String,

    val eatTime: String,
    val sleepTime: String,
    val wakeTime: String
)