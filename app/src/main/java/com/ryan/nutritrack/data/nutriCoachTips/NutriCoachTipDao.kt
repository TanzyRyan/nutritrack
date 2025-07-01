package com.ryan.nutritrack.data.nutriCoachTips

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NutriCoachTipDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nutriCoachTip: NutriCoachTip)

    @Query("SELECT * FROM NutriCoachTips where patientId = :patientId")
    suspend fun getTipByPatientId(patientId: Int): List<NutriCoachTip>
}