package com.ryan.nutritrack.data

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ryan.nutritrack.data.network.FruitResponseModel
import com.ryan.nutritrack.data.nutriCoachTips.NutriCoachTip
import com.ryan.nutritrack.data.patients.Patient
import kotlinx.coroutines.launch

class NutriCoachViewModel (context: Context) : ViewModel() {
    private val fruitRepo: FruitRepository = FruitRepository()

    var searchedInput by mutableStateOf("")
        private set


    var searchedFamily by mutableStateOf("")
        private set


    var searchedCalories by mutableStateOf("")
        private set


    var searchedFat by mutableStateOf("")
        private set


    var searchedSugar by mutableStateOf("")
        private set

    var searchedCarb by mutableStateOf("")
        private set


    var searchedProtein by mutableStateOf("")
        private set

    fun updateSearchedInput(newValue: String) {
        searchedInput = newValue
    }

    fun updateAllSearched(fruit: FruitResponseModel) {
        searchedFamily = fruit.family
        searchedCalories = fruit.nutritions["calories"].toString()
        searchedFat = fruit.nutritions["fat"].toString()
        searchedSugar = fruit.nutritions["sugar"].toString()
        searchedCarb = fruit.nutritions["carbohydrates"].toString()
        searchedProtein = fruit.nutritions["protein"].toString()
    }


    var promptResult by mutableStateOf("")
        private set

    fun updatePromptResult(newValue: String) {
        promptResult = newValue
    }

    var showTipsState by mutableStateOf(false)
        private set

    fun updateShowTipsState(newValue: Boolean) {
        showTipsState = newValue
    }


    var allPromptResults by mutableStateOf<List<NutriCoachTip>>(emptyList())
        private set


    fun updateAllPromptResults(newValue: List<NutriCoachTip>) {
        allPromptResults = newValue
    }

    var patient by mutableStateOf<Patient?>(null)
        private set

    fun updatePatient(newValue: Patient) {
        patient = newValue
    }

    var tipInserted by mutableStateOf(false)
        private set

    fun updateTipInserted(newValue: Boolean) {
        tipInserted = newValue
    }


    fun updateFruit(fruitName: String) {
        viewModelScope.launch {
            val result = fruitRepo.getFruit(fruitName)
            if(result != null) {
                updateAllSearched(result)
            }
        }
    }


    class NutriCoachViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            NutriCoachViewModel(context) as T
    }
}
