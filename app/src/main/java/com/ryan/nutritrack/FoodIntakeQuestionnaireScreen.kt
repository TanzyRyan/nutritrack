package com.ryan.nutritrack


import com.ryan.nutritrack.ui.theme.DigitalNutritionTheme


import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.ryan.nutritrack.data.PatientAuthManager
import com.ryan.nutritrack.data.foodIntakes.FoodIntake
import com.ryan.nutritrack.data.foodIntakes.FoodIntakeViewModel
import com.ryan.nutritrack.data.foodIntakes.FoodIntakeViewModel.FoodIntakeViewModelFactory
import com.ryan.nutritrack.data.patients.PatientViewModel
import com.ryan.nutritrack.data.patients.PatientViewModel.PatientViewModelFactory
import com.ryan.nutritrack.data.QuestionnaireViewModel

@Composable
fun FoodIntakeQuestionnaire(
    patientViewModel: PatientViewModel,
    foodIntakeViewModel: FoodIntakeViewModel,
    userId: String,
    modifier: Modifier = Modifier,
    questionnaireViewModel: QuestionnaireViewModel
) {
    val context = LocalContext.current
    val patientFoodIntake = questionnaireViewModel.patientFoodIntake


    val foodCategories = listOf(
        "Vegetables",
        "Fruits",
        "Red Meat",
        "Fish",
        "Grains",
        "Seafood",
        "Poultry",
        "Eggs",
        "NutsSeeds"
    )

    val personaList = (listOf(
        "Health Devotee",
        "Mindful Eater",
        "Wellness Striver",
        "Balance Seeker",
        "Health Procrastinator",
        "Food Carefree"
    ))
    val personaDescription = (listOf(
        "I’m passionate about healthy eating & health plays a big part in my life. I use social media to follow active lifestyle personalities or get new recipes/exercise ideas. I may even buy superfoods or follow a particular type of diet. I like to think I am super healthy.",
        "I’m health-conscious and being healthy and eating healthy is important to me. Although health means different things to different people, I make conscious lifestyle decisions about eating based on what I believe healthy means. I look for new recipes and healthy eating information on social media.",
        "I aspire to be healthy (but struggle sometimes). Healthy eating is hard work! I’ve tried to improve my diet, but always find things that make it difficult to stick with the changes. Sometimes I notice recipe ideas or healthy eating hacks, and if it seems easy enough, I’ll give it a go.",
        "I try and live a balanced lifestyle, and I think that all foods are okay in moderation. I shouldn’t have to feel guilty about eating a piece of cake now and again. I get all sorts of inspiration from social media like finding out about new restaurants, fun recipes and sometimes healthy eating tips.",
        "I’m contemplating healthy eating but it’s not a priority for me right now. I know the basics about what it means to be healthy, but it doesn’t seem relevant to me right now. I have taken a few steps to be healthier but I am not motivated to make it a high priority because I have too many other things going on in my life.",
        "I’m not bothered about healthy eating. I don’t really see the point and I don’t think about it. I don’t really notice healthy eating tips or recipes and I don’t care what I eat.",
    ))
    val personaImages = listOf(
        R.drawable.persona_1,
        R.drawable.persona_2,
        R.drawable.persona_3,
        R.drawable.persona_4,
        R.drawable.persona_5,
        R.drawable.persona_6
    )

    val personaInfo = personaList.indices.map { index ->
        Triple(
            personaList[index],
            personaDescription[index],
            personaImages[index]
        )
    }

    val checkboxStates = questionnaireViewModel.checkboxStates
    val modalStates = questionnaireViewModel.modalStates
    val selectedPersona = questionnaireViewModel.selectedPersona
    val expanded = questionnaireViewModel.expanded

    val eatTime = questionnaireViewModel.eatTime
    val eatTimePickerDialog = TimePickerFun(eatTime)

    val sleepTime = questionnaireViewModel.sleepTime
    val sleepTimePickerDialog = TimePickerFun(sleepTime)

    val wakeTime = questionnaireViewModel.wakeTime
    val wakeTimePickerDialog = TimePickerFun(wakeTime)




    LaunchedEffect(Unit) {
        if(!questionnaireViewModel.hasInitialise) {
            questionnaireViewModel.updateHasInitialise(true)

            foodIntakeViewModel.initialiseUserFoodIntake(userId.toInt())
//            delay(500)
            val intake = foodIntakeViewModel.getFoodIntakeById(userId.toInt())
            questionnaireViewModel.updatePatientFoodIntake(intake)

            checkboxStates["Vegetables"] = intake.vegetables
            checkboxStates["Fruits"] = intake.fruits
            checkboxStates["Red Meat"] = intake.redMeat
            checkboxStates["Fish"] = intake.fish
            checkboxStates["Grains"] = intake.grains
            checkboxStates["Seafood"] = intake.seafood
            checkboxStates["Poultry"] = intake.poultry
            checkboxStates["Eggs"] = intake.eggs
            checkboxStates["NutsSeeds"] = intake.nutSeeds

            questionnaireViewModel.updateSelectedPersona(intake.persona)
            questionnaireViewModel.updateEatTime(intake.eatTime)
            questionnaireViewModel.updateSleepTime(intake.sleepTime)
            questionnaireViewModel.updateWakeTime(intake.wakeTime)
        }

    }

    if (patientFoodIntake == null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
        }
    } else {



        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {


            // top
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
                        text = "Food Intake Questionnaire",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))


                // food categories
                Text(
                    text = "Tick all the food categories you can eat",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    foodCategories.chunked(3).forEach { rowItems ->
                        Column() {
                            rowItems.forEach { category ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = checkboxStates[category] ?: false,
                                        onCheckedChange = { questionnaireViewModel.updateCheckbox(category, it) },
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Text(text = category, fontSize = 14.sp)
                                }
                            }
                        }

                    }
                }

                Spacer(modifier = Modifier.height(45.dp))


                // persona
                Text(text = "Your Persona", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    "People can be broadly classified into 6 different types based on their eating preferences. CLick on each button below to find out the different types, and select the type that best fits you!",
                    fontSize = 14.sp, lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(10.dp))


                personaInfo.chunked(2).forEach { chunk ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        chunk.forEach { (persona, description, image) ->
                            Button(
                                onClick = { questionnaireViewModel.updateModalStates(persona, true) },
                                colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
                            ) {
                                Text(text = persona, fontSize = 14.sp)
                            }
                            if (modalStates[persona] == true) {
                                AlertDialog(
                                    onDismissRequest = { questionnaireViewModel.updateModalStates(persona, false) },
                                    text = {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center,
                                            modifier = Modifier.padding(16.dp)
                                        ) {
                                            androidx.compose.foundation.Image(
                                                painter = painterResource(id = image),
                                                contentDescription = "$persona image",
                                                modifier = Modifier.size(130.dp)
                                            )

                                            Spacer(modifier = Modifier.height(16.dp))
                                            Text(
                                                text = persona,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )
                                            Spacer(modifier = Modifier.height(16.dp))
                                            Text(
                                                text = description,
                                                fontSize = 14.sp
                                            )
                                        }
                                    },
                                    confirmButton = {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Button(
                                                onClick = { modalStates[persona] = false },
                                                colors = ButtonDefaults.buttonColors(
                                                    Color(
                                                        0xFF6200EE
                                                    )
                                                )
                                            ) {
                                                Text("Dismiss")
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }



                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Which persona best fits you?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {questionnaireViewModel.updateExpanded(true)}
                            .height(48.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (selectedPersona == "") "Select Persona" else selectedPersona,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 15.dp)
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Dropdown Arrow"
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { questionnaireViewModel.updateExpanded(false) },
                        modifier= Modifier
                            .verticalScroll(rememberScrollState())
                            .heightIn(max = 275.dp)) {
                        personaList.forEach { persona ->
                            DropdownMenuItem(
                                text = { Text(text = persona) },
                                onClick = {
                                    questionnaireViewModel.updateSelectedPersona(persona)
                                    questionnaireViewModel.updateExpanded(true)
                                }
                            )
                        }
                    }
                }


                // timings
                Spacer(modifier = Modifier.height(45.dp))

                Text(text = "Timings", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text(
                        "What time of day approx. do you normally eat your biggest meal?",
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Column() {
                        Button(onClick = { eatTimePickerDialog.show() }) {
                            Text(text = "Open Time Picker", fontSize = 14.sp)
                        }
                        Text("Selected time: ${eatTime.value}", fontSize = 14.sp)
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "What time of day approx. do you go to sleep at night",
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Column() {
                        Button(onClick = { sleepTimePickerDialog.show() }) {
                            Text(text = "Open Time Picker", fontSize = 14.sp)
                        }
                        Text("Selected time: ${sleepTime.value}", fontSize = 14.sp)
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "What time of day approx. do you wake up in the morning",
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Column() {
                        Button(onClick = { wakeTimePickerDialog.show() }) {
                            Text(text = "Open Time Picker", fontSize = 14.sp)
                        }
                        Text("Selected time: ${wakeTime.value}", fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            if (
//                                selectedPersona != "Select Persona"
                                selectedPersona != ""
                                && eatTime.value != ""
                                && sleepTime.value != ""
                                && wakeTime.value != ""
                                && (checkboxStates.values.any { it })
                            ) {

                                if (
                                    (eatTime.value != sleepTime.value) &&
                                    (eatTime.value != wakeTime.value) &&
                                    (sleepTime.value != wakeTime.value)
                                ) {
                                    patientViewModel.updatePatientFirstTimeUser(
                                        userId.toInt(),
                                        true
                                    )
                                    Toast.makeText(context, "Answers saved", Toast.LENGTH_SHORT)
                                        .show()

                                    val foodIntake = FoodIntake(
                                        patientId = userId.toInt(),
                                        vegetables = checkboxStates["Vegetables"] ?: false,
                                        fruits = checkboxStates["Fruits"] ?: false,
                                        redMeat = checkboxStates["Red Meat"] ?: false,
                                        fish = checkboxStates["Fish"] ?: false,
                                        grains = checkboxStates["Grains"] ?: false,
                                        seafood = checkboxStates["Seafood"] ?: false,
                                        poultry = checkboxStates["Poultry"] ?: false,
                                        eggs = checkboxStates["Eggs"] ?: false,
                                        nutSeeds = checkboxStates["NutsSeeds"] ?: false,
                                        persona = selectedPersona,
                                        eatTime = eatTime.value,
                                        sleepTime = sleepTime.value,
                                        wakeTime = wakeTime.value
                                    )

                                    foodIntakeViewModel.update(foodIntake)
                                    val intent = Intent(context, HomeScreen::class.java)
                                    context.startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Timings must all be different",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            } else {
                                Toast.makeText(
                                    context,
                                    "Please complete all questions",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .height(40.dp)
                            .width(150.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
                    ) { Text("Save") }
                }
            }
        }
    }
}




@Composable
fun TimePickerFun(time: MutableState<String>): TimePickerDialog {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    calendar.time = Calendar.getInstance().time
    return TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            val formattedHour = String.format("%02d", selectedHour)
            val formattedMinute = String.format("%02d", selectedMinute)

            time.value = "$formattedHour:$formattedMinute"
        },
        hour, minute, false
    )
}





class FoodIntakeQuestionnaireScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userId = PatientAuthManager.getStudentId()

        val foodIntakeViewModel: FoodIntakeViewModel = ViewModelProvider(
            this, FoodIntakeViewModel.FoodIntakeViewModelFactory(this@FoodIntakeQuestionnaireScreen)
        )[FoodIntakeViewModel::class.java]

        val patientViewModel: PatientViewModel = ViewModelProvider(
            this, PatientViewModel.PatientViewModelFactory(this@FoodIntakeQuestionnaireScreen)
        )[PatientViewModel::class.java]

        val questionnaireViewModel: QuestionnaireViewModel = ViewModelProvider(
            this, QuestionnaireViewModel.QuestionnaireViewModelFactory(this@FoodIntakeQuestionnaireScreen)
        )[QuestionnaireViewModel::class.java]

        setContent {
            DigitalNutritionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FoodIntakeQuestionnaire(
                        patientViewModel = patientViewModel,
                        foodIntakeViewModel = foodIntakeViewModel,
                        userId = userId.toString(),
                        modifier = Modifier.padding(innerPadding),
                        questionnaireViewModel = questionnaireViewModel
                    )
                }
            }
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun FoodIntakeQuestionnairePreview() {
//    val userId = "28"
//    DigitalNutritionTheme {
//        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//            FoodIntakeQuestionnaire(userId = userId, modifier = Modifier.padding(innerPadding))
//        }
//    }
//}

