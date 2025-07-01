package com.ryan.nutritrack

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ryan.nutritrack.data.RegisterViewModel
import com.ryan.nutritrack.data.RegisterViewModel.RegisterViewModelFactory
import com.ryan.nutritrack.data.patients.PatientViewModel
import com.ryan.nutritrack.data.patients.PatientViewModel.PatientViewModelFactory
import com.ryan.nutritrack.ui.theme.DigitalNutritionTheme
import kotlinx.coroutines.launch
@Composable
fun Register(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val registerViewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModelFactory(context)
    )
    val patientViewModel: PatientViewModel = viewModel(
        factory = PatientViewModelFactory(context)
    )

    val passwdOne = registerViewModel.passwdOne
    val passwdTwo = registerViewModel.passwdTwo
    val selectedPatientId = registerViewModel.patientId
    val inputPhn = registerViewModel.inputPhoneNumber
    val patientName = registerViewModel.patientName

    val toastContent = registerViewModel.toastContent
    val successfulReg = registerViewModel.successfulReg

    val expanded = registerViewModel.expanded

    val coroutineScope = rememberCoroutineScope()

    val patientIdList by patientViewModel.allPatientId.collectAsState(initial = emptyList())

    val regAttempt = registerViewModel.regAttempt


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
                text = "Register",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(65.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { registerViewModel.updateExpanded(true) }
                        .height(48.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (selectedPatientId == -1) "Select ID" else selectedPatientId.toString(),
                        modifier = Modifier.weight(1f).padding(start = 15.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown Arrow"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { registerViewModel.updateExpanded(false) },
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .heightIn(max = 275.dp)
                ) {
                    patientIdList.forEach { id ->
                        DropdownMenuItem(
                            text = {Text(text = id.toString()) },
                            onClick = {
                                registerViewModel.updatePatientId(id)
                                registerViewModel.updateExpanded(false)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = inputPhn,
                onValueChange = { registerViewModel.updateInputPhoneNumber(it) },
                label = { Text(text = "Enter your phone number") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = patientName,
                onValueChange = { registerViewModel.updatePatientName(it) },
                label = { Text(text = "Enter your name") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = passwdOne,
                onValueChange = { registerViewModel.updatePasswdOne(it) },
                label = { Text(text = "Enter your password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = passwdTwo,
                onValueChange = { registerViewModel.updatePasswdTwo(it) },
                label = { Text(text = "Enter your password again") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )


            Spacer(modifier = Modifier.height(20.dp))

            Text("This app is only for pre-registered users. Please have your ID, phone number and password to claim your account.",
                fontSize = 12.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(35.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        val phoneNumber = patientViewModel.getPhoneNumberByPatientId(selectedPatientId)
                        val patientName = patientViewModel.getPatientNameByPatientId(selectedPatientId)

                        registerViewModel.checkReg(phoneNumber, patientName)
                    }
                },

                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
            ) {
                Text("Register")
            }

            if (regAttempt) {
            Toast.makeText(context, toastContent, Toast.LENGTH_SHORT).show()
            if (successfulReg) {
                val capitalizedName = patientName.lowercase().replaceFirstChar { it.uppercaseChar() }
                patientViewModel.updatePatientPassword(selectedPatientId, passwdOne)
                patientViewModel.updatePatientName(selectedPatientId, capitalizedName)
            }

            registerViewModel.resetRegAttempt()
        }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {context.startActivity(Intent(context, LoginScreen::class.java))},
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







class RegisterScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DigitalNutritionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Register(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DigitalNutritionTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Register(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}