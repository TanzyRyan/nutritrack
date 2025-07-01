package com.ryan.nutritrack.data.foodIntakes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodIntakeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(foodIntake: FoodIntake)


    @Query("SELECT * FROM FoodIntakes")
    fun getAllFoodIntakes(): Flow<List<FoodIntake>>

    @Query("SELECT * FROM FoodIntakes WHERE patientId = :patientId")
    suspend fun getFoodIntakeById(patientId: Int): FoodIntake

    @Update
    suspend fun update(foodIntake: FoodIntake)
}