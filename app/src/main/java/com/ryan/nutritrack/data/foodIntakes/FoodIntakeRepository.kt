package com.ryan.nutritrack.data.foodIntakes

import android.content.Context
import com.ryan.nutritrack.data.AppDatabase
import kotlinx.coroutines.flow.Flow

class FoodIntakeRepository {
    var foodIntakeDao: FoodIntakeDao

    constructor(context: Context) {
        foodIntakeDao = AppDatabase.getDatabase(context).foodIntakeDao()
    }

    suspend fun insert(foodIntake: FoodIntake) {
        foodIntakeDao.insert(foodIntake)
    }

    fun getAllFoodIntakes():   Flow<List<FoodIntake>> = foodIntakeDao.getAllFoodIntakes()

    suspend fun getFoodIntakeById(patientId: Int): FoodIntake {
        return foodIntakeDao.getFoodIntakeById(patientId)
    }

    suspend fun update(foodIntake: FoodIntake) {
        foodIntakeDao.update(foodIntake)
    }

    suspend fun initialiseUserFoodIntake(userId: Int) {
        insert(
            FoodIntake(
                patientId = userId.toInt(),
                vegetables = false,
                fruits = false,
                redMeat = false,
                fish = false,
                grains = false,
                seafood = false,
                poultry = false,
                eggs = false,
                nutSeeds = false,
                persona = "",
                eatTime = "",
                sleepTime = "",
                wakeTime = ""
            )
        )
    }
}
