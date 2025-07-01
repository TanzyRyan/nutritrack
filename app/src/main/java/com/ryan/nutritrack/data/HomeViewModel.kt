package com.ryan.nutritrack.data

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ryan.nutritrack.data.patients.Patient
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider

class HomeViewModel (context: Context) : ViewModel() {
    var patient by mutableStateOf<Patient?>(null)
        private set

    fun updatePatient(newValue: Patient) {
        patient = newValue
    }

     class HomeViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            HomeViewModel(context) as T
    }
}
