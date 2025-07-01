package com.ryan.nutritrack.data

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ryan.nutritrack.data.patients.Patient
import com.ryan.nutritrack.data.patients.PatientRepository
import kotlinx.coroutines.launch

class LoginViewModel (context: Context) : ViewModel() {
    private val patientRepo = PatientRepository(context) //fix this?
    var inputPasswd by mutableStateOf("")
        private set

    var successfulLogin by mutableStateOf(false)
        private set

    var patientId by mutableIntStateOf(0)
        private set

    fun updatePatientId(id: Int) {
        patientId = id
    }

    fun updateInputPasswd(passwd: String) {
        inputPasswd = passwd
    }

    var toastContent by mutableStateOf("")
        private set

    fun resetRegAttempt() {
        loginAttempt = false
    }

    var loginAttempt by mutableStateOf(false)
        private set

    fun updateLoginAttempt(newValue: Boolean) {
        loginAttempt = newValue
    }

    var expanded by mutableStateOf(false)
        private set

    fun updateExpanded(newValue: Boolean) {
        expanded = newValue
    }

    var patient by mutableStateOf<Patient?>(null)
        private set

    fun updatePatient(newValue: Patient) {
        patient = newValue
    }


    fun checkPasswd() {
        viewModelScope.launch {
            val patient = patientRepo.getPatientById(patientId)
            if (patient.password == inputPasswd) {
                successfulLogin = true
            } else {
                successfulLogin = false
            }
        }
    }





    class LoginViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            LoginViewModel(context) as T
    }
}