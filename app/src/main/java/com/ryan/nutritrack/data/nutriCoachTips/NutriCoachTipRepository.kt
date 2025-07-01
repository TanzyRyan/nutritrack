package com.ryan.nutritrack.data.nutriCoachTips

import android.content.Context
import com.ryan.nutritrack.data.AppDatabase

class NutriCoachTipRepository {
    var nutriCoachTipDao: NutriCoachTipDao

    constructor(context: Context) {
        nutriCoachTipDao = AppDatabase.getDatabase(context).NutriCoachTipDao()
    }

    suspend fun insert(nutriCoachTip: NutriCoachTip) {
        nutriCoachTipDao.insert(nutriCoachTip)
    }

    suspend fun getTipByPatientId(patientId: Int): List<NutriCoachTip> {
            return nutriCoachTipDao.getTipByPatientId(patientId)
    }


}