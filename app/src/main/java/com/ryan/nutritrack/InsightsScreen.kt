package com.ryan.nutritrack


import com.ryan.nutritrack.ui.theme.DigitalNutritionTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.LinearProgressIndicator
import android.content.Intent
import android.content.Intent.ACTION_SEND
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModelProvider
import com.ryan.nutritrack.data.InsightsViewModel
import com.ryan.nutritrack.data.PatientAuthManager
import com.ryan.nutritrack.data.patients.PatientViewModel


@Composable
fun Insights(
    patientViewModel: PatientViewModel,
    userId: String,
    modifier: Modifier = Modifier,
    insightsViewModel: InsightsViewModel
) {
    val context = LocalContext.current
    val userScore = insightsViewModel.userScore

    LaunchedEffect(Unit) {
        insightsViewModel.updateUserScore(patientViewModel.getAllScores(userId.toInt()))
    }

    if (userScore == emptyList<Float>()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
        }
    } else {
        val scoreText = "My total score on NutriTrack is ${userScore[0]}!"

        val foodCategories = listOf(
            "Vegetable",
            "Fruit",
            "Grains and Cereals",
            "Whole Grain",
            "Meat and Alternatives",
            "Dairy and Alternatives",
            "Water",
            "Unsaturated Fats",
            "Saturated Fats",
            "Sodium",
            "Sugar",
            "Alcohol",
            "Discretionary"
        )

        val outOf = listOf(
            10f,
            10f,
            10f,
            10f,
            10f,
            10f,
            5f,
            10f,
            10f,
            10f,
            10f,
            5f,
            10f
        )



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
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {

                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Insights: Food Score",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(25.dp))
                    }


                    for (i in foodCategories.indices) {
                        ProgressBarRow(foodCategories[i], userScore[i + 1], outOf[i])
                    }

                    Spacer(modifier = Modifier.height(25.dp))



                    Text(text = "Total Score", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        LinearProgressIndicator(
                            progress = { userScore[0] / 100f },
                            modifier = Modifier.weight(2f).padding(end = 10.dp).height(8.dp),
                            color = Color(0xFF6200EE),
                            trackColor = Color(0xFFDBBDFF)
                        )
                        Text(text = "${userScore[0]}/100")
                    }



                    Spacer(modifier = Modifier.height(30.dp))



                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                val shareIntent = Intent(ACTION_SEND)
                                shareIntent.type = "text/plain"
                                shareIntent.putExtra(Intent.EXTRA_TEXT, scoreText)
                                context.startActivity(
                                    Intent.createChooser(
                                        shareIntent,
                                        "share via"
                                    )
                                )
                            },
                            modifier = Modifier.height(35.dp).width(220.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text("Share with Someone")
                                Icon(
                                    imageVector = Icons.Filled.Share,
                                    contentDescription = "Share",
                                    modifier = Modifier.padding(start = 5.dp)
                                )
                            }
                        }


                        Spacer(modifier = Modifier.height(10.dp))


                        Button(
                            onClick = {
                                val intent = Intent(context, NutriCoachScreen::class.java)
                                context.startActivity(intent)
                            },
                            modifier = Modifier.height(35.dp).width(180.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Improve my Diet!")
                                Icon(
                                    imageVector = Icons.Filled.ThumbUp,
                                    contentDescription = "improve",
                                    modifier = Modifier.padding(start = 5.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProgressBarRow(category: String, progress: Float, maxProgress: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = category, modifier = Modifier.weight(1.2f))
        LinearProgressIndicator(
            progress = { progress / maxProgress },
            modifier = Modifier.weight(2f).padding(end = 10.dp).height(8.dp),
            color = Color(0xFF6200EE),
            trackColor = Color(0xFFDBBDFF),
        )
        Text(text = "${(progress)}/${maxProgress.toInt()}", modifier = Modifier.weight(0.65f))
    }
    Spacer(modifier = Modifier.height(8.dp))
}









class InsightsScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userId = PatientAuthManager.getStudentId()
        val patientViewModel: PatientViewModel = ViewModelProvider(
            this, PatientViewModel.PatientViewModelFactory(this@InsightsScreen)
        )[PatientViewModel::class.java]

        val insightsViewModel: InsightsViewModel = ViewModelProvider(
            this, InsightsViewModel.InsightsViewModelFactory(this@InsightsScreen)
        )[InsightsViewModel::class.java]

        setContent {
            DigitalNutritionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Insights(
                        patientViewModel = patientViewModel,
                        userId = userId.toString(),
                        modifier = Modifier.padding(innerPadding),
                        insightsViewModel = insightsViewModel
                    )
                }
            }
        }
    }
}


