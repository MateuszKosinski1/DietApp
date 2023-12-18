package com.example.dietapp.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dietapp.Databases.Activity
import com.google.firebase.Firebase
import com.google.firebase.database.database

/**
 * Composable reprezentujący ekran dodawania treningu.
 *
 * @param navController NavController do nawigacji między ekranami.
 */
@Composable
fun AddActivity(navController: NavController) {
    // Stan reprezentujący nazwę aktywności
    var activityName by remember { mutableStateOf("") }
    // Stan reprezentujący spalone kalorie
    var caloriesBurned by remember { mutableStateOf("") }

    // Komponenty interfejsu użytkownika związane z dodawaniem aktywności
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Nagłówek
        Text(text = "Twoje treningi")

        // Przycisk do powrotu do głównego ekranu
        Button(onClick = {
            navController.navigate("main")
        }) {
            Text(text = "Główny ekran")
        }

        // Pole tekstowe dla nazwy aktywności
        TextField(
            value = activityName,
            onValueChange = { activityName = it },
            label = { Text("Nazwa produktu") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Pole tekstowe dla spalonych kalorii
        TextField(
            value = caloriesBurned,
            onValueChange = { caloriesBurned = it },
            label = { Text("Kalorie") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Przycisk dodawania aktywności
        Button(
            onClick = {
                addActivity(activityName, caloriesBurned)
                // Wyczyszczenie pól po dodaniu aktywności
                activityName = ""
                caloriesBurned = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Dodaj Trening")
        }
    }
}

/**
 * Funkcja dodająca aktywność do bazy danych.
 *
 * @param activityName Nazwa aktywności.
 * @param caloriesBurned Spalone kalorie.
 */
private fun addActivity(
    activityName: String,
    caloriesBurned: String
) {
    // Inicjalizacja bazy danych Firebase
    val database = Firebase.database
    val myRef = database.getReference("activities")

    // Sprawdzenie, czy pola nie są puste
    if (activityName.isNotBlank() && caloriesBurned.isNotBlank()) {
        // Tworzenie obiektu aktywności
        val activity = Activity(
            activityName,
            caloriesBurned,
        )

        // Dodawanie aktywności do bazy danych
        val activityId = myRef.push().key
        if (activityId != null) {
            myRef.child(activityId).setValue(activity)
            // Opcjonalnie: Można dodać obsługę powrotu do poprzedniej aktywności lub wyświetlenie komunikatu o sukcesie
        }
    }
}
