package com.example.dietapp.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DietScreen(navController: NavController) {
    // Główna aktywność ekranu diety
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Przycisk do nawigacji do głównego ekranu
        Button(
            onClick = { navController.navigate("main") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Główny ekran")
        }

        // Przycisk do nawigacji do ekranu dzisiejszego spożycia
        Button(
            onClick = { navController.navigate("dailyconsumption") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Dzisiejsze spożycie")
        }

        // Przycisk do nawigacji do dziennika posiłków
        Button(
            onClick = { navController.navigate("mealdiary") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Dodaj posiłek który zjadłeś")
        }
    }
}