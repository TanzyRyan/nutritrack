package com.ryan.nutritrack.data

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InsightsViewModel(context: Context): ViewModel() {
    var userScore by mutableStateOf<List<Float>>(emptyList())
        private set

    fun updateUserScore(newValue: List<Float>) {
        userScore = newValue
    }

    class InsightsViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            InsightsViewModel(context) as T
    }
}