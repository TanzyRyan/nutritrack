package com.ryan.nutritrack.data.nutriCoachTips

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NutriCoachTips")
data class NutriCoachTip(
    @PrimaryKey(autoGenerate = true)
    val nutriCoachTipId: Int = 0,

    val patientId: Int,
    val promptResult: String
)

