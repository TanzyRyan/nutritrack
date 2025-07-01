package com.ryan.nutritrack.data.patients

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(patient: Patient)

    @Query("SELECT * FROM Patients")
    fun getAllPatients(): Flow<List<Patient>>

    @Query("DELETE FROM Patients")
    suspend fun deleteAllPatients()

    @Query("SELECT patientId FROM Patients")
    fun getAllPatientId(): Flow<List<Int>>

    @Query("SELECT * FROM Patients WHERE patientId = :patientId")
    suspend fun getPatientById(patientId: Int): Patient

    @Query("UPDATE Patients SET password = :newPassword WHERE patientId = :patientId")
    suspend fun updatePatientPasswd(patientId: Int, newPassword: String)

    @Query("SELECT phoneNumber FROM Patients WHERE patientId = :patientId")
    suspend fun getPhoneNumberByPatientId(patientId: Int): String

    @Query("UPDATE Patients SET name = :newName WHERE patientId = :patientId")
    suspend fun updatePatientName(patientId: Int, newName: String)

    @Query("SELECT name FROM Patients WHERE patientId = :patientId")
    suspend fun getPatientNameByPatientId(patientId: Int): String

    @Query("SELECT AVG(totalScore) FROM Patients WHERE sex = :sex")
    suspend fun getAverageScoreBySex(sex: String): Float

    @Query("UPDATE Patients SET firstTimeUser = :newValue WHERE patientId = :patientId")
    suspend fun updatePatientFirstTimeUser(patientId: Int, newValue: Boolean)


}