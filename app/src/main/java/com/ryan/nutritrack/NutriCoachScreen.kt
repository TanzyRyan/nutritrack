package com.ryan.nutritrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.ryan.nutritrack.data.PatientAuthManager
import com.ryan.nutritrack.ui.theme.DigitalNutritionTheme
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ryan.nutritrack.data.GenAiViewModel
import com.ryan.nutritrack.data.NutriCoachViewModel
import com.ryan.nutritrack.data.UiState
import com.ryan.nutritrack.data.nutriCoachTips.NutriCoachTip
import com.ryan.nutritrack.data.nutriCoachTips.NutriCoachTipViewModel
import com.ryan.nutritrack.data.patients.PatientViewModel
import androidx.compose.foundation.lazy.items

@Composable
fun NutriCoach(
    modifier: Modifier = Modifier,
    userId: String,
    nutriCoachTipViewModel: NutriCoachTipViewModel,
    nutriCoachViewModel: NutriCoachViewModel,
    patientViewModel: PatientViewModel
) {

    val searchedInput = nutriCoachViewModel.searchedInput
    val searchedFamily = nutriCoachViewModel.searchedFamily
    val searchedCalories = nutriCoachViewModel.searchedCalories
    val searchedFat = nutriCoachViewModel.searchedFat
    val searchedSugar = nutriCoachViewModel.searchedSugar
    val searchedCarb = nutriCoachViewModel.searchedCarb
    val searchedProtein = nutriCoachViewModel.searchedProtein

    val promptResult = nutriCoachViewModel.promptResult
    val showTipsState = nutriCoachViewModel.showTipsState
    val allPromptResults = nutriCoachViewModel.allPromptResults

    val coroutineScope = rememberCoroutineScope()
    val genAiViewModel: GenAiViewModel = viewModel()
    val uiState by genAiViewModel.uiState.collectAsState()
    val patient = nutriCoachViewModel.patient

    val nutritionDetails = listOf(
        "Family" to searchedFamily,
        "Calories" to searchedCalories,
        "Fat" to searchedFat,
        "Sugar" to searchedSugar,
        "Carbohydrates" to searchedCarb,
        "Protein" to searchedProtein
    )

    val tipInserted = nutriCoachViewModel.tipInserted


    LaunchedEffect(userId) {
        nutriCoachViewModel.updatePatient(patientViewModel.getPatientById(userId.toInt()))
    }


    Scaffold(
        bottomBar = {
            BottomNavigationBar(userId)
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(45.dp))

                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "NutriCoach",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(55.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchedInput,
                        onValueChange = {
                            nutriCoachViewModel.updateSearchedInput(it)
                        },
                        label = { Text(text = "Fruit Name") },
                        modifier = Modifier
                            .weight(1.7f)
                            .height(60.dp)
                    )
                    

                    Spacer(modifier = Modifier.width(15.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                nutriCoachViewModel.updateFruit(searchedInput)

                            }
                        },
                        modifier = Modifier
                            .height(60.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "search icon")
                        Spacer(modifier = Modifier.width(5.dp))
                        Text("Details")
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                nutritionDetails.forEach { (label, value) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = label,
                            fontSize = 15.sp,
                            modifier = Modifier.weight(0.8f)
                        )

                        Text(
                            text = ":",
                            fontSize = 15.sp,
                            modifier = Modifier.weight(0.2f)
                        )

                        Text(
                            text = value,
                            fontSize = 15.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }


                Spacer(modifier = Modifier.height(10.dp))


                Button(
                    onClick = {
                        nutriCoachViewModel.updateTipInserted(false)
                        genAiViewModel.sendPrompt("based on this $patient, the fruit score out of 10, generate a short encouraging message to help someone improve their fruit intake," +
                                "it can be involved in various ways such as increasing fruit intake and variety of fruits.")
                    },
                    modifier = Modifier
                        .height(48.dp)
                        .width(250.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
                ) {
                    Icon(Icons.Filled.FavoriteBorder, contentDescription = "message icon")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Motivational Message (AI)")
                }

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            nutriCoachViewModel.updateAllPromptResults(nutriCoachTipViewModel.getTipByPatientId(userId.toInt()))
                            nutriCoachViewModel.updateShowTipsState(true)
                        }
                    },
                    modifier = Modifier
                        .height(48.dp)
                        .width(250.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
                ) {
                    Text("Show All Tips")
                }

                if (showTipsState) {
                    AlertDialog(
                        onDismissRequest = { nutriCoachViewModel.updateShowTipsState(false) },

                        text = {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth()
                            ){
                                items(allPromptResults) {tip ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                            .shadow(
                                                elevation = 8.dp,
                                                shape = RoundedCornerShape(8.dp),
                                                ambientColor = Color.Gray,
                                                spotColor = Color.Black
                                            )
                                    ) {
                                        Text(tip.promptResult,  modifier = Modifier.padding(16.dp))
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
//                                    onClick = { showTipsState= false },
                                    onClick = { nutriCoachViewModel.updateShowTipsState(false) },
                                    colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
                                ) {
                                    Text("Dismiss")
                                }
                            }
                        }

                    )

                }

                Spacer(modifier = Modifier.height(20.dp))

                if (uiState is UiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    var textColor = MaterialTheme.colorScheme.onSurface

                    if (uiState is UiState.Error) {
                        textColor = MaterialTheme.colorScheme.error
                        nutriCoachViewModel.updatePromptResult((uiState as UiState.Error).errorMessage)

                    } else if (uiState is UiState.Success) {
                        textColor = MaterialTheme.colorScheme.onSurface
                        nutriCoachViewModel.updatePromptResult((uiState as UiState.Success).outputText)
                    }
                }

                LaunchedEffect(uiState) {
                    val currentState = uiState
                    if (currentState is UiState.Success && !nutriCoachViewModel.tipInserted) {
                        val tip = NutriCoachTip(
                            patientId = userId.toInt(),
                            promptResult = currentState.outputText
                        )
                        nutriCoachTipViewModel.insert(tip)
                        nutriCoachViewModel.updateTipInserted(true)
                    }
                }

                Text(
                    text = promptResult,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}


class NutriCoachScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userId = PatientAuthManager.getStudentId()
        val nutriCoachTipViewModel: NutriCoachTipViewModel = ViewModelProvider(
            this, NutriCoachTipViewModel.NutriCoachTipViewModelFactory(this@NutriCoachScreen)
        )[NutriCoachTipViewModel::class.java]

        val nutriCoachViewModel: NutriCoachViewModel = ViewModelProvider(
            this, NutriCoachViewModel.NutriCoachViewModelFactory(this@NutriCoachScreen)
        )[NutriCoachViewModel::class.java]

        val patientViewModel: PatientViewModel = ViewModelProvider(
            this, PatientViewModel.PatientViewModelFactory(this@NutriCoachScreen)
        )[PatientViewModel::class.java]

        setContent {
            DigitalNutritionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NutriCoach(
                        modifier = Modifier.padding(innerPadding),
                        userId = userId.toString(),
                        nutriCoachTipViewModel = nutriCoachTipViewModel,
                        nutriCoachViewModel = nutriCoachViewModel,
                        patientViewModel = patientViewModel
                    )
                }
            }
        }
    }
}
