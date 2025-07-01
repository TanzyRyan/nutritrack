package com.ryan.nutritrack.data.nutriCoachTips

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.Int

class NutriCoachTipViewModel(context: Context) : ViewModel() {
    private val nutriCoachTipRepo = NutriCoachTipRepository(context)

    suspend fun getTipByPatientId(patientId: Int): List<NutriCoachTip> {
        return nutriCoachTipRepo.getTipByPatientId(patientId)
    }

    fun insert(nutriCoachTip: NutriCoachTip) = viewModelScope.launch {
        nutriCoachTipRepo.insert(nutriCoachTip)
    }



    class NutriCoachTipViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            NutriCoachTipViewModel(context) as T
    }
}
