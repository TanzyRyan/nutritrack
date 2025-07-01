package com.ryan.nutritrack


import com.ryan.nutritrack.ui.theme.DigitalNutritionTheme
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.ryan.nutritrack.data.HomeViewModel
import com.ryan.nutritrack.data.PatientAuthManager
import com.ryan.nutritrack.data.patients.PatientViewModel

@Composable
fun Home(
    patientViewModel: PatientViewModel,
    userId: String,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel
) {
    val context = LocalContext.current
    val patient = homeViewModel.patient

    LaunchedEffect(Unit) {
        homeViewModel.updatePatient(patientViewModel.getPatientById(userId.toInt()))
    }

    if (patient == null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
        }
    } else {
        var totalScore = patient.totalScore.toFloat()
        var name = patient.name


        Scaffold(
            bottomBar = {
                BottomNavigationBar(userId)
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(rememberScrollState()),
                color = Color.White
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        Text(text = "Hello,", fontSize = 20.sp)
                        Text("User $name", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "You’ve already filled in your Food Intake Questionnaire, but you can change details here:",
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Button(
                                onClick = {
                                    val intent = Intent(
                                        context,
                                        FoodIntakeQuestionnaireScreen::class.java
                                    ).apply {
                                        putExtra("userId", userId)
                                    }
                                    context.startActivity(intent)
                                },
                                colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))

                            ) {
                                Text("Edit")
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        androidx.compose.foundation.Image(
                            painter = painterResource(id = R.drawable.food_plate),
                            contentDescription = "food plate",
                            modifier = Modifier.size(300.dp)
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "My Score",
                                modifier = Modifier.weight(1f),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Text(
                                text = "See all scores >>",
                                fontSize = 16.sp,
                                modifier = Modifier.clickable {
                                    val intent = Intent(context, InsightsScreen::class.java).apply {
                                        putExtra("userId", userId)
                                    }
                                    context.startActivity(intent)
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Your Food Quality score", modifier = Modifier.weight(1f))
                            Text(
                                text = "${totalScore}/100",
                                color = Color(0xFF00D100),
                                fontWeight = FontWeight.Bold
                            )
                        }


                        Spacer(modifier = Modifier.height(10.dp))

                        HorizontalDivider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )

                        Text(
                            text = "What is the Food Quality Score?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Your Food Quality Score provides a snapshot of how well your eating patterns align with established food guidelines, helping you identify both strengths and opportunities for improvement in your diet.",
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "This personalised measurement considers various food groups including vegetables, fruits, whole grains, and proteins to give you practical insights for making healthier food choices",
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                        )

                    }
                }
            }
        }
    }
}







@Composable
fun BottomNavigationBar(userId: String) {
    val context = LocalContext.current


    Column {
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp
        )

        BottomAppBar(
            modifier = Modifier.height(70.dp),
            containerColor = Color.White
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        val intent = Intent(context, HomeScreen::class.java)
                        context.startActivity(intent)
                        },
                    modifier = Modifier.size(75.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Home, contentDescription = "home icon")
                        Text(text = "Home", fontSize = 10.sp)

                    }
                }

                IconButton(
                    onClick = {
                        val intent = Intent(context, InsightsScreen::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.size(75.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Info, contentDescription = "insight icon")
                        Text(text = "Insight", fontSize = 10.sp)
                    }
                }

                IconButton(
                    onClick = {
                        val intent = Intent(context, NutriCoachScreen::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.size(75.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Face, contentDescription = "NutriCoach icon")
                        Text(text = "NutriCoach", fontSize = 10.sp)
                    }
                }

                IconButton(
                    onClick = {
                        val intent = Intent(context, SettingsScreen::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.size(75.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Settings, contentDescription = "setting icon")
                        Text(text = "Settings", fontSize = 10.sp)
                    }
                }
            }
        }
    }
}










class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userId = PatientAuthManager.getStudentId()

        val patientViewModel: PatientViewModel = ViewModelProvider(
            this, PatientViewModel.PatientViewModelFactory(this@HomeScreen)
        )[PatientViewModel::class.java]

        val homeViewModel: HomeViewModel = ViewModelProvider(
            this, HomeViewModel.HomeViewModelFactory(this@HomeScreen)
        )[HomeViewModel::class.java]

        setContent {
            DigitalNutritionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Home(patientViewModel = patientViewModel,
                        userId = userId.toString(),
                        modifier = Modifier.padding(innerPadding),
                        homeViewModel = homeViewModel
                    )
                }
            }
        }
    }
}
