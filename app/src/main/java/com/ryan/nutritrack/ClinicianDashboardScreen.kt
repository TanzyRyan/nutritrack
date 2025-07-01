package com.ryan.nutritrack

import android.content.Intent
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ryan.nutritrack.data.GenAiViewModel
import com.ryan.nutritrack.data.PatientAuthManager
import com.ryan.nutritrack.data.UiState
import com.ryan.nutritrack.data.patients.PatientViewModel
import com.ryan.nutritrack.ui.theme.DigitalNutritionTheme
import androidx.compose.runtime.LaunchedEffect
import com.ryan.nutritrack.data.ClinicianDashboardViewModel

@Composable
fun ClinicianDashboard(modifier: Modifier = Modifier,
                       userId: String,
                       patientViewModel: PatientViewModel,
                       clinicianDashboardViewModel: ClinicianDashboardViewModel
) {

    val context = LocalContext.current
    val avgMaleHEIF = clinicianDashboardViewModel.avgMaleHEIF
    val avgFemaleHEIF = clinicianDashboardViewModel.avgFemaleHEIF
    val promptResult = clinicianDashboardViewModel.promptResult
    val genAiViewModel: GenAiViewModel = viewModel()
    val uiState by genAiViewModel.uiState.collectAsState()
    val allPatientsInfo = patientViewModel.allPatients.collectAsState(initial = emptyList()).value

    LaunchedEffect(Unit) {
        clinicianDashboardViewModel.updateAvgHEIF(
            patientViewModel.getAverageScoreBySex("Male").toString(),
            patientViewModel.getAverageScoreBySex("Female").toString()
        )
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
                        text = "Clinician Dashboard",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(55.dp))


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
                        text = "Average HEIF (Male)",
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1.2f)
                    )

                    Text(
                        text = ":",
                        fontSize = 15.sp,
                        modifier = Modifier.weight(0.2f)
                    )

                    Text(
                        text = avgMaleHEIF,
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))


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
                        text = "Average HEIF (Female)",
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1.2f)
                    )

                    Text(
                        text = ":",
                        fontSize = 15.sp,
                        modifier = Modifier.weight(0.2f)
                    )

                    Text(
                        text = avgFemaleHEIF,
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        onClick = {
                            genAiViewModel.sendPrompt("${allPatientsInfo}, based on the data, display 3 interesting data patterns without mentioning specific userid or names, dont provide introduction ")
                        },
                        modifier = Modifier
                            .height(48.dp)
                            .width(175.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
                    ) {
                        Text("Find Data Patterns")
                    }

                    Button(
                        onClick = {
                            val intent = Intent(context, HomeScreen::class.java)
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .height(48.dp)
                            .width(175.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
                    ) {
                        Text("Exit Dashboard")
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                if (uiState is UiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    var textColor = MaterialTheme.colorScheme.onSurface

                    if (uiState is UiState.Error) {
                        textColor = MaterialTheme.colorScheme.error
                        clinicianDashboardViewModel.updatePromptResult((uiState as UiState.Error).errorMessage)

                    } else if (uiState is UiState.Success) {
                        textColor = MaterialTheme.colorScheme.onSurface
                        clinicianDashboardViewModel.updatePromptResult((uiState as UiState.Success).outputText)
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




class ClinicianDashboardScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userId = PatientAuthManager.getStudentId()

        val patientViewModel: PatientViewModel = ViewModelProvider(
            this, PatientViewModel.PatientViewModelFactory(this@ClinicianDashboardScreen)
        )[PatientViewModel::class.java]

        val clinicianDashboardViewModel: ClinicianDashboardViewModel = ViewModelProvider(
            this, ClinicianDashboardViewModel.ClinicianDashboardViewModelFactory(this@ClinicianDashboardScreen)
        )[ClinicianDashboardViewModel::class.java]

        setContent {
            DigitalNutritionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ClinicianDashboard(
                        modifier = Modifier.padding(innerPadding),
                        userId = userId.toString(),
                        patientViewModel = patientViewModel,
                        clinicianDashboardViewModel = clinicianDashboardViewModel
                    )
                }
            }
        }
    }
}

