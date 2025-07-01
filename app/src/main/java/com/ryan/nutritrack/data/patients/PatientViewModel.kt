package com.ryan.nutritrack.data.patients

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.Int
import kotlin.String

class PatientViewModel(context: Context) : ViewModel() {
    private val patientRepo = PatientRepository(context)
    val allPatients: Flow<List<Patient>> = patientRepo.getAllPatients()

    //inserts a patient into the repo
    fun insert(patient: Patient) = viewModelScope.launch {
        patientRepo.insert(patient)
    }

    fun deleteAllPatients() = viewModelScope.launch {
        patientRepo.deleteAllPatients()
    }


    val allPatientId: Flow<List<Int>> = patientRepo.getAllPatientId()

    suspend fun getPatientById(patientId: Int): Patient {
        return patientRepo.getPatientById(patientId)
    }


    fun updatePatientPassword(patientId: Int, newPassword: String) {
        viewModelScope.launch {
            patientRepo.updatePatientPassword(patientId, newPassword)
        }
    }

    fun updatePatientFirstTimeUser(patientId: Int, newValue: Boolean) {
        viewModelScope.launch {
            patientRepo.updatePatientFirstTimeUser(patientId, newValue)
        }
    }

    fun updatePatientName(patientId: Int, newName: String) {
        viewModelScope.launch {
            patientRepo.updatePatientName(patientId, newName)
        }
    }

    suspend fun getPhoneNumberByPatientId(patientId: Int): String {
        return patientRepo.getPhoneNumberByPatientId(patientId)
    }

    suspend fun getPatientNameByPatientId(patientId: Int): String {
        return patientRepo.getPatientNameByPatientId(patientId)
    }

    suspend fun getAverageScoreBySex(sex: String): Float {
        return patientRepo.getAverageScoreBySex(sex)
    }

    suspend fun getAllScores(patientId: Int): List<Float> {
        var user = getPatientById(patientId)
        return listOf(
            user.totalScore.toFloat(),
            user.discretionaryScore.toFloat(),
            user.vegetableScore.toFloat(),
            user.fruitScore.toFloat(),
            user.grainAndCerealScore.toFloat(),
            user.wholeGrainsScore.toFloat(),
            user.meatAndAltScore.toFloat(),
            user.dairyAndAltScore.toFloat(),
            user.sodiumScore.toFloat(),
            user.alcoholScore.toFloat(),
            user.waterScore.toFloat(),
            user.sugarScore.toFloat(),
            user.saturatedFatScore.toFloat(),
            user.unsaturatedFatScore.toFloat()
        )
    }

    fun csvToDatabase(fileName: String, context: Context) = viewModelScope.launch {
        patientRepo.csvToDatabase(fileName, context)
    }

    class PatientViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            PatientViewModel(context) as T
    }
}

