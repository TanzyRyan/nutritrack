package com.ryan.nutritrack.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object PatientAuthManager {
    val _patientId: MutableState<String?> = mutableStateOf(null)

    fun login(patientId: String) {
        _patientId.value = patientId
    }

    fun logout() {
        _patientId.value = null
    }

    fun getStudentId(): String? {
        return _patientId.value
    }
}