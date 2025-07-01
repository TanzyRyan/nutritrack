package com.ryan.nutritrack.data

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ryan.nutritrack.data.foodIntakes.FoodIntake

class QuestionnaireViewModel (context: Context) : ViewModel() {


    val checkboxStates = mutableStateMapOf(
        "Vegetables" to false,
        "Fruits" to false,
        "Red Meat" to false,
        "Fish" to false,
        "Grains" to false,
        "Seafood" to false,
        "Poultry" to false,
        "Eggs" to false,
        "NutsSeeds" to false
    )

    fun updateCheckbox(category: String, newValue: Boolean) {
        checkboxStates[category] = newValue
    }

    var hasInitialise by mutableStateOf(false)
        private set

    fun updateHasInitialise(newValue: Boolean) {
        hasInitialise = newValue
    }


    var patientFoodIntake by mutableStateOf<FoodIntake?>(null)
        private set

    fun updatePatientFoodIntake(newValue: FoodIntake) {
        patientFoodIntake = newValue
    }

    val modalStates = mutableStateMapOf(
        "Health Devotee" to false,
        "Mindful Eater" to false,
        "Wellness Striver" to false,
        "Balance Seeker" to false,
        "Health Procrastinator" to false,
        "Food Carefree" to false
    )

    fun updateModalStates(persona: String, newValue: Boolean) {
        modalStates[persona] = newValue
    }

    var selectedPersona by mutableStateOf("")
        private set

    fun updateSelectedPersona(persona: String) {
        selectedPersona = persona
    }

    var expanded by mutableStateOf(false)
        private set

    fun updateExpanded(newValue: Boolean) {
        expanded = newValue
    }

    var wakeTime = mutableStateOf("")
        private set

    fun updateWakeTime(newValue: String) {
        wakeTime.value = newValue
    }

    var eatTime = mutableStateOf("")
        private set

    fun updateEatTime(newValue: String) {
        eatTime.value = newValue
    }

    var sleepTime = mutableStateOf("")
        private set

    fun updateSleepTime(newValue: String) {
        sleepTime.value = newValue
    }



    class QuestionnaireViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            QuestionnaireViewModel(context) as T
    }
}
