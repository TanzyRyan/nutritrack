package com.ryan.nutritrack

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.ryan.nutritrack.data.ClinicianLoginViewModel
import com.ryan.nutritrack.data.PatientAuthManager
import com.ryan.nutritrack.ui.theme.DigitalNutritionTheme


@Composable
fun ClinicianLogin(modifier: Modifier = Modifier,
                   userId: String,
                   clinicianLoginViewModel: ClinicianLoginViewModel
) {
    val context = LocalContext.current


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
                        text = "Clinician Login",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(55.dp))

                OutlinedTextField(
                    value = clinicianLoginViewModel.inputKey,
                    onValueChange = { clinicianLoginViewModel.changeInputKey(it) },
                    label = { Text(text = "Enter your clinician key") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(35.dp))

                Button(
                    onClick = {
                        clinicianLoginViewModel.validateLogin(
                            onSuccess = {
                                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                                val intent = Intent(context, ClinicianDashboardScreen::class.java)
                                context.startActivity(intent)
                            },
                            onError = {
                                Toast.makeText(context, "Login Unsuccessful", Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
                ) {
                    Text("Login")
                }
            }
        }
    }
}






class ClinicianLoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userId = PatientAuthManager.getStudentId()

        val clinicianLoginViewModel: ClinicianLoginViewModel = ViewModelProvider(
            this, ClinicianLoginViewModel.ClinicianLoginViewModelFactory(this@ClinicianLoginScreen)
        )[ClinicianLoginViewModel::class.java]

        setContent {
            DigitalNutritionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ClinicianLogin(
                        modifier = Modifier.padding(innerPadding),
                        userId = userId.toString(),
                        clinicianLoginViewModel = clinicianLoginViewModel
                    )
                }
            }
        }
    }
}
