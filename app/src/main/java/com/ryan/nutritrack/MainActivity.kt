package com.ryan.nutritrack

import android.content.Intent
import com.ryan.nutritrack.ui.theme.DigitalNutritionTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModelProvider
import com.ryan.nutritrack.data.patients.PatientViewModel


// ╔═══════════════════════════════════════════════════╗
// ║ 🚨🚨🚨 PLEASE READ BEFORE RUNNING THE APP 🚨🚨🚨 ║
// ╚═══════════════════════════════════════════════════╝
// Please insert your own Gemini API key into the local property file using the following line:
// apiKey={your api key}
// All dropdown menu have their size limited but they are scrollable


@Composable
fun Welcome(
    modifier: Modifier = Modifier,
    patientViewModel: PatientViewModel
) {
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        color = Color.White

    ) {
        Column (
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text="NutriTrack",
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(text="This app provides general health and nutrition information for educational purposes only. It is not intended as medical advice, diagnosis, or treatment. Always consult a qualified healthcare professional before making any changes to your diet, exercise, or health regimen.",
                textAlign = TextAlign.Center,
                fontSize = 14.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Use this app at your own risk.",
                fontSize = 14.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Text("If you’d like to see an Accredited Practicing Dietitian (APD), please visit the Monash Nutrition/Dietetics Clinic (discounted rates for students):",
                textAlign = TextAlign.Center,
                fontSize = 14.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition",
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                fontSize = 14.sp)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val intent = Intent(context, LoginScreen::class.java)
                    context.startActivity(intent)

                },
                modifier = Modifier.height(50.dp).fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(95.dp))

            Text(
                text="Designed by Yong Zheng Ryan Tan",
                fontSize = 14.sp
            )

        }
    }
}


// ╔════════════════════════════════════════════════════╗
// ║ 🚨🚨🚨 PLEASE READ BEFORE RUNNING THE APP 🚨🚨🚨 ║
// ╚════════════════════════════════════════════════════╝
// Please insert your own Gemini API key into the local property file using the following line:
// apiKey={your api key}
// All dropdown menu have their size limited but they are scrollable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val patientViewModel: PatientViewModel = ViewModelProvider(
            this, PatientViewModel.PatientViewModelFactory(this@MainActivity)
        )[PatientViewModel::class.java]

        patientViewModel.csvToDatabase("UserDetails.csv", this)

        setContent {
            DigitalNutritionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Welcome(
                        modifier = Modifier.padding(innerPadding),
                        patientViewModel = patientViewModel
                        )
                }
            }
        }
    }
}