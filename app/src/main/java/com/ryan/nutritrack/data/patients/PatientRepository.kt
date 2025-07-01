package com.ryan.nutritrack.data.patients

import android.content.Context
import com.ryan.nutritrack.data.AppDatabase
import kotlinx.coroutines.flow.Flow
import java.io.BufferedReader
import java.io.InputStreamReader

class PatientRepository {
    var patientDao: PatientDao
    constructor(context: Context) {
        patientDao = AppDatabase.getDatabase(context).patientDao()
    }
    suspend fun insert(patient: Patient) {
        patientDao.insert(patient)
    }

    suspend fun deleteAllPatients() {
        patientDao.deleteAllPatients()
    }

    suspend fun updatePatientPassword(patientId: Int, newPassword: String) {
        patientDao.updatePatientPasswd(patientId, newPassword)
    }

    suspend fun getPhoneNumberByPatientId(patientId: Int): String {
        return patientDao.getPhoneNumberByPatientId(patientId)
    }

    suspend fun updatePatientName(patientId: Int, newName: String) {
        patientDao.updatePatientName(patientId, newName)
    }

    suspend fun getPatientNameByPatientId(patientId: Int): String {
        return patientDao.getPatientNameByPatientId(patientId)
    }

    suspend fun updatePatientFirstTimeUser(patientId: Int, newValue: Boolean) {
        patientDao.updatePatientFirstTimeUser(patientId, newValue)
    }

    suspend fun getPatientById(patientId: Int): Patient = patientDao.getPatientById(patientId)

    fun getAllPatientId(): Flow<List<Int>> = patientDao.getAllPatientId()

    fun getAllPatients():   Flow<List<Patient>> = patientDao.getAllPatients()

    suspend fun getAverageScoreBySex(sex: String): Float {
        return patientDao.getAverageScoreBySex(sex)
    }


    suspend fun csvToDatabase(fileName: String, context: Context) {
        var assets = context.assets
        try {
            val inputStream = assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val lines = reader.readLines()

            // remove byte order mark from the header
            val header = lines[0].replace("\uFEFF", "").split(",")
            val data = lines.drop(1)


            for (line in data) {
                val splitLine = line.split(",")

                if (splitLine[header.indexOf("Sex")].lowercase() == "male") {
                    insert(
                        Patient(
                            patientId = splitLine[header.indexOf("User_ID")].toInt(),
                            name = "",
                            password = "",
                            firstTimeUser = false,
                            phoneNumber = splitLine[header.indexOf("PhoneNumber")],
                            sex = splitLine[header.indexOf("Sex")],

                            totalScore = splitLine[header.indexOf("HEIFAtotalscoreMale")].toDouble(),
                            discretionaryScore = splitLine[header.indexOf("DiscretionaryHEIFAscoreMale")].toDouble(),
                            vegetableScore = splitLine[header.indexOf("VegetablesHEIFAscoreMale")].toDouble(),
                            fruitScore = splitLine[header.indexOf("FruitHEIFAscoreMale")].toDouble(),
                            grainAndCerealScore = splitLine[header.indexOf("GrainsandcerealsHEIFAscoreMale")].toDouble(),
                            wholeGrainsScore = splitLine[header.indexOf("WholegrainsHEIFAscoreMale")].toDouble(),
                            meatAndAltScore = splitLine[header.indexOf("MeatandalternativesHEIFAscoreMale")].toDouble(),
                            dairyAndAltScore = splitLine[header.indexOf("DairyandalternativesHEIFAscoreMale")].toDouble(),
                            sodiumScore = splitLine[header.indexOf("SodiumHEIFAscoreMale")].toDouble(),
                            alcoholScore = splitLine[header.indexOf("AlcoholHEIFAscoreMale")].toDouble(),
                            waterScore = splitLine[header.indexOf("WaterHEIFAscoreMale")].toDouble(),
                            sugarScore = splitLine[header.indexOf("SugarHEIFAscoreMale")].toDouble(),
                            saturatedFatScore = splitLine[header.indexOf("SaturatedFatHEIFAscoreMale")].toDouble(),
                            unsaturatedFatScore = splitLine[header.indexOf("UnsaturatedFatHEIFAscoreMale")].toDouble()
                        )
                    )
                } else {
                    insert(
                        Patient(
                            patientId = splitLine[header.indexOf("User_ID")].toInt(),
                            name = "",
                            password = "",
                            firstTimeUser = false,
                            phoneNumber = splitLine[header.indexOf("PhoneNumber")],
                            sex = splitLine[header.indexOf("Sex")],

                            totalScore = splitLine[header.indexOf("HEIFAtotalscoreFemale")].toDouble(),
                            discretionaryScore = splitLine[header.indexOf("DiscretionaryHEIFAscoreFemale")].toDouble(),
                            vegetableScore = splitLine[header.indexOf("VegetablesHEIFAscoreFemale")].toDouble(),
                            fruitScore = splitLine[header.indexOf("FruitHEIFAscoreFemale")].toDouble(),
                            grainAndCerealScore = splitLine[header.indexOf("GrainsandcerealsHEIFAscoreFemale")].toDouble(),
                            wholeGrainsScore = splitLine[header.indexOf("WholegrainsHEIFAscoreFemale")].toDouble(),
                            meatAndAltScore = splitLine[header.indexOf("MeatandalternativesHEIFAscoreFemale")].toDouble(),
                            dairyAndAltScore = splitLine[header.indexOf("DairyandalternativesHEIFAscoreFemale")].toDouble(),
                            sodiumScore = splitLine[header.indexOf("SodiumHEIFAscoreFemale")].toDouble(),
                            alcoholScore = splitLine[header.indexOf("AlcoholHEIFAscoreFemale")].toDouble(),
                            waterScore = splitLine[header.indexOf("WaterHEIFAscoreFemale")].toDouble(),
                            sugarScore = splitLine[header.indexOf("SugarHEIFAscoreFemale")].toDouble(),
                            saturatedFatScore = splitLine[header.indexOf("SaturatedFatHEIFAscoreFemale")].toDouble(),
                            unsaturatedFatScore = splitLine[header.indexOf("UnsaturatedFatHEIFAscoreFemale")].toDouble()
                        )
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}