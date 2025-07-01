package com.ryan.nutritrack.data.foodIntakes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FoodIntakeViewModel (context: Context) : ViewModel() {
    private val foodIntakeRepo = FoodIntakeRepository(context)

    val allFoodIntakes: Flow<List<FoodIntake>> = foodIntakeRepo.getAllFoodIntakes()

    fun insert(foodIntake: FoodIntake) = viewModelScope.launch {
        foodIntakeRepo.insert(foodIntake)
    }

    suspend fun getFoodIntakeById(patientId: Int): FoodIntake {
            return foodIntakeRepo.getFoodIntakeById(patientId)

    }
    fun update(foodIntake: FoodIntake) = viewModelScope.launch {
        foodIntakeRepo.update(foodIntake)
    }

    fun initialiseUserFoodIntake(userId: Int) {
        viewModelScope.launch {
            foodIntakeRepo.initialiseUserFoodIntake(userId)
        }
    }


    class FoodIntakeViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            FoodIntakeViewModel(context) as T
    }
}