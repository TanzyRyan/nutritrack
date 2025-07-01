package com.ryan.nutritrack.data

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ClinicianLoginViewModel (context: Context) : ViewModel() {
    var inputKey by mutableStateOf("")
        private set

    fun changeInputKey(newInput: String) {
        inputKey = newInput
    }

    fun validateLogin(onSuccess: () -> Unit, onError: () -> Unit) {
        if (inputKey == "dollar-entry-apples") {
            onSuccess()
        } else {
            onError()
        }
    }

    class ClinicianLoginViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ClinicianLoginViewModel(context) as T
    }
}