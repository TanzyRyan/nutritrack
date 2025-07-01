package com.ryan.nutritrack.data.patients

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Patients")
data class Patient(

    @PrimaryKey
    val patientId: Int,

    val name: String,
    val password: String,
    val firstTimeUser: Boolean,
    val phoneNumber: String,
    val sex: String,

    val totalScore: Double,
    val discretionaryScore: Double,
    val vegetableScore: Double,
    val fruitScore: Double,
    val grainAndCerealScore: Double,
    val wholeGrainsScore: Double,
    val meatAndAltScore: Double,
    val dairyAndAltScore: Double,
    val sodiumScore: Double,
    val alcoholScore: Double,
    val waterScore: Double,
    val sugarScore: Double,
    val saturatedFatScore: Double,
    val unsaturatedFatScore: Double
)