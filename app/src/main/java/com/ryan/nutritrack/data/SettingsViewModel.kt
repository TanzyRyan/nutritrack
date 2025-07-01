package com.ryan.nutritrack.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import com.ryan.nutritrack.data.patients.Patient
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider

class SettingsViewModel (context: Context) : ViewModel() {
    var patient by mutableStateOf<Patient?>(null)
        private set

    fun updatePatient(newValue: Patient) {
        patient = newValue
    }

    class SettingsViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SettingsViewModel(context) as T
    }

}
