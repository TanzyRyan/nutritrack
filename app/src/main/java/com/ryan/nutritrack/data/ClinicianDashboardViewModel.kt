package com.ryan.nutritrack.data

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ClinicianDashboardViewModel (context: Context) : ViewModel() {

    var avgMaleHEIF by mutableStateOf("")
        private set

    var avgFemaleHEIF by mutableStateOf("")
        private set

    var promptResult by mutableStateOf("")
        private set

    fun updatePromptResult(newValue: String) {
        promptResult = newValue
    }

    fun updateAvgHEIF(maleValue: String, femaleValue: String) {
        avgMaleHEIF = maleValue
        avgFemaleHEIF = femaleValue
    }



    class ClinicianDashboardViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ClinicianDashboardViewModel(context) as T
    }



}

