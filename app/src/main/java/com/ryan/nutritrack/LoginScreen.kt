package com.ryan.nutritrack

import com.ryan.nutritrack.ui.theme.DigitalNutritionTheme
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ryan.nutritrack.data.LoginViewModel
import com.ryan.nutritrack.data.LoginViewModel.LoginViewModelFactory
import com.ryan.nutritrack.data.PatientAuthManager
import com.ryan.nutritrack.data.patients.PatientViewModel
import com.ryan.nutritrack.data.patients.PatientViewModel.PatientViewModelFactory
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModelProvider

@Composable
fun Login(
    modifier: Modifier = Modifier,
    patientViewModel: PatientViewModel,
    loginViewModel: LoginViewModel) {

    val context = LocalContext.current
    val inputPasswd = loginViewModel.inputPasswd
    val successfulLogin = loginViewModel.successfulLogin
    val selectedPatientId = loginViewModel.patientId
    val loginAttempt = loginViewModel.loginAttempt
    val expanded = loginViewModel.expanded
    val coroutineScope = rememberCoroutineScope()
    val patientIdList by patientViewModel.allPatientId.collectAsState(initial = emptyList())


    LaunchedEffect(loginAttempt) {
        if (loginAttempt) {
            if (successfulLogin) {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

                val patient = patientViewModel.getPatientById(selectedPatientId)
                PatientAuthManager.login(selectedPatientId.toString())

                if (patient.firstTimeUser == false) {
                    val intent = Intent(context, FoodIntakeQuestionnaireScreen::class.java)
                    context.startActivity(intent)

                } else {
                    val intent = Intent(context, HomeScreen::class.java)
                    context.startActivity(intent)
                }
            } else {
                Toast.makeText(context, "Login Unsuccessful", Toast.LENGTH_SHORT).show()
            }
            loginViewModel.updateLoginAttempt(false)
        }
    }



    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(45.dp))

            Text(
                text = "Log In",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(65.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { loginViewModel.updateExpanded(true) }
                        .height(48.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (selectedPatientId == 0) "Select ID" else selectedPatientId.toString(),
                        modifier = Modifier.weight(1f).padding(start = 15.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown Arrow"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { loginViewModel.updateExpanded(false) },
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .heightIn(max = 275.dp)
                    ) {
                    patientIdList.forEach { id ->
                        DropdownMenuItem(
                            text = {Text(text = id.toString()) },
                            onClick = {
                                loginViewModel.updatePatientId(id)
                                loginViewModel.updateExpanded(false)

                            }
                        )
                    }
                }
            }



            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = inputPasswd,
                onValueChange = { loginViewModel.updateInputPasswd(it) },
                label = { Text(text = "Enter your password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("This app is only for pre-registered patients. Please have your ID and phone number handy before continuing.",
                fontSize = 12.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(25.dp))


            Button(
                onClick = {
                    loginViewModel.checkPasswd()
                    loginViewModel.updateLoginAttempt(true)
                },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
            ) {
                Text("Login")
            }


            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {context.startActivity(Intent(context, RegisterScreen::class.java))},
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
            ) {
                Text("Register")
            }
        }
    }
}








class LoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val patientViewModel: PatientViewModel = ViewModelProvider(
            this, PatientViewModel.PatientViewModelFactory(this@LoginScreen)
        )[PatientViewModel::class.java]

        val loginViewModel: LoginViewModel = ViewModelProvider(
            this, LoginViewModel.LoginViewModelFactory(this@LoginScreen)
        )[LoginViewModel::class.java]

        setContent {
            DigitalNutritionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Login(
                        modifier = Modifier.padding(innerPadding),
                        patientViewModel = patientViewModel,
                        loginViewModel = loginViewModel
                    )
                }
            }
        }
    }
}

