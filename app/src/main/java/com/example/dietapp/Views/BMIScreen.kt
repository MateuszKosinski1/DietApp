package com.example.dietapp.Views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun BMICalcScreen(navController: NavController) {
    // Stan reprezentujący wagę
    var weight by remember { mutableStateOf("") }
    // Stan reprezentujący wzrost
    var height by remember { mutableStateOf("") }
    // Stan reprezentujący wynik BMI
    var bmi by remember { mutableFloatStateOf(0f) }

    // Komponenty interfejsu użytkownika związane z kalkulatorem BMI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Nagłówek
        Text(text = "BMI kalkulator")

        // Przycisk powrotu do głównego ekranu
        Button(onClick = {
            navController.navigate("main")
        }) {
            Text(text = "Strona Główna")
        }

        // Pole do wprowadzania wagi
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Waga (kg)") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    // Przejdź do pola wzrostu po naciśnięciu "Next" na klawiaturze
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Pole do wprowadzania wzrostu
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Wzrost (cm)") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Przycisk do obliczania BMI
        Button(
            onClick = {
                // Oblicz BMI po naciśnięciu przycisku
                if (weight.isNotEmpty() && height.isNotEmpty()) {
                    val weightFloat = weight.toFloat()
                    val heightFloat = height.toFloat() / 100 // konwersja wzrostu na metry
                    bmi = weightFloat / (heightFloat * heightFloat)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Oblicz BMI")
        }

        // Wyświetlenie wyniku BMI
        if (bmi > 0) {
            Text(
                "Twoje BMI: ${"%.2f".format(bmi)}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                color = Color.Black,
            )
        }
    }
}