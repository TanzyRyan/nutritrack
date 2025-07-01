package com.ryan.nutritrack.data

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegisterViewModel (context: Context) : ViewModel() {
//    private val patientRepo = PatientRepository(context)

    var passwdOne by mutableStateOf("")
        private set

    var passwdTwo by mutableStateOf("")
        private set

    var patientId by mutableIntStateOf(-1)
        private set

    var inputPhoneNumber by mutableStateOf("")
        private set

    var toastContent by mutableStateOf("")
        private set

    var successfulReg by mutableStateOf(false)
        private set

    var patientName by mutableStateOf("")

    fun updatePatientName(name: String) {
        patientName = name
    }

    fun updatePasswdOne(passwd: String) {
        passwdOne = passwd
    }

    fun updatePasswdTwo(passwd: String) {
        passwdTwo = passwd
    }

    fun updateInputPhoneNumber(phoneNumber: String) {
        inputPhoneNumber = phoneNumber
    }

    fun updatePatientId(id: Int) {
        patientId = id
    }

    var expanded by mutableStateOf(false)
        private set

    fun updateExpanded(newValue: Boolean) {
        expanded = newValue
    }

    fun resetRegAttempt() {
        regAttempt = false
    }

    var regAttempt by mutableStateOf(false)
        private set


    fun checkReg(phoneNumber: String, name: String) {
        viewModelScope.launch {
//            val phoneNumber = patientRepo.getPhoneNumberByPatientId(patientId)
//            val name = patientRepo.getPatientNameByPatientId(patientId)

            if (name != "") {
                toastContent = "This patient has already registered"
                regAttempt = true
            } else {
                if (patientId == -1 || inputPhoneNumber.isBlank() || passwdOne.isBlank() || passwdTwo.isBlank() || patientName.isBlank()) {
                    toastContent = "Fill in all fields"
                    regAttempt = true
                } else {
                    if (inputPhoneNumber != phoneNumber) {
                        toastContent = "Wrong patient id or phone number"
                        regAttempt = true
                    } else {
                        if (passwdOne != passwdTwo) {
                            toastContent = "Passwords do not match"
                            regAttempt = true
                        } else {
                            if (passwdOne.length < 8) {
                                toastContent = "Password must be at least 8 characters long"
                                regAttempt = true
                            } else {
                                toastContent = "Registration successful"
                                successfulReg = true
                                regAttempt = true
                            }

                        }
                    }
                }
            }
        }
    }




    class RegisterViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            RegisterViewModel(context) as T
    }
}