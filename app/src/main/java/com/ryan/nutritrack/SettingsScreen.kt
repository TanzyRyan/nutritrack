package com.ryan.nutritrack

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ryan.nutritrack.ui.theme.DigitalNutritionTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.ryan.nutritrack.data.PatientAuthManager
import com.ryan.nutritrack.data.SettingsViewModel
import com.ryan.nutritrack.data.patients.PatientViewModel


@Composable
fun SetScreen(
    modifier: Modifier = Modifier,
    userId: String,
    patientViewModel: PatientViewModel,
    settingsViewModel: SettingsViewModel
) {

    val context = LocalContext.current
    val patient = settingsViewModel.patient

    LaunchedEffect(Unit) {
        settingsViewModel.updatePatient(patientViewModel.getPatientById(userId.toInt()))
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
        Scaffold(
            bottomBar = {
                BottomNavigationBar(userId)
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(45.dp))

                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Settings",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(55.dp))

                    Text("Account", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(30.dp))


                    Row() {
                        Icon(Icons.Filled.Person, contentDescription = "face icon")
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "name: ${patient!!.name}", fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row() {
                        Icon(Icons.Filled.Call, contentDescription = "call icon")
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "phone number: ${patient!!.phoneNumber}", fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row() {
                        Icon(Icons.Filled.AccountBox, contentDescription = "account box icon")
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "ID: ${patient!!.patientId.toString()}", fontSize = 18.sp)

                    }

                    Spacer(modifier = Modifier.height(65.dp))


                    Text("Other Settings", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                PatientAuthManager.logout()
                                val intent = Intent(context, LoginScreen::class.java)
                                context.startActivity(intent)
                            }
                    ) {
                        Icon(Icons.Filled.Lock, contentDescription = "lock icon")
                        Spacer(modifier = Modifier.width(20.dp))
                        Text("Logout", fontSize = 18.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "right arrow"
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.clickable {
                            val intent = Intent(context, ClinicianLoginScreen::class.java)
                            context.startActivity(intent)
                        }
                    ) {
                        Icon(Icons.Filled.Person, contentDescription = "person icon")
                        Spacer(modifier = Modifier.width(20.dp))
                        Text("Clinician Login", fontSize = 18.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "right arrow"
                        )
                    }

                    Spacer(modifier = Modifier.height(80.dp))



                }
            }
        }
    }
}





class SettingsScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userId = PatientAuthManager.getStudentId()
        val patientViewModel: PatientViewModel = ViewModelProvider(
            this, PatientViewModel.PatientViewModelFactory(this@SettingsScreen)
        )[PatientViewModel::class.java]

        val settingsViewModel: SettingsViewModel = ViewModelProvider(
            this, SettingsViewModel.SettingsViewModelFactory(this@SettingsScreen)
        )[SettingsViewModel::class.java]


        setContent {
            DigitalNutritionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SetScreen(
                        modifier = Modifier.padding(innerPadding),
                        userId = userId.toString(),
                        patientViewModel = patientViewModel,
                        settingsViewModel = settingsViewModel
                    )
                }
            }
        }
    }
}
