package com.ryan.nutritrack.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ryan.nutritrack.data.foodIntakes.FoodIntake
import com.ryan.nutritrack.data.foodIntakes.FoodIntakeDao
import com.ryan.nutritrack.data.nutriCoachTips.NutriCoachTipDao
import com.ryan.nutritrack.data.nutriCoachTips.NutriCoachTip

import com.ryan.nutritrack.data.patients.Patient
import com.ryan.nutritrack.data.patients.PatientDao

@Database(entities = [Patient::class, FoodIntake::class, NutriCoachTip::class], version = 1, exportSchema = false)

abstract class AppDatabase: RoomDatabase() {

    abstract fun patientDao(): PatientDao
    abstract fun foodIntakeDao(): FoodIntakeDao
    abstract fun NutriCoachTipDao(): NutriCoachTipDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .build().also { Instance = it }
            }
        }
    }
}