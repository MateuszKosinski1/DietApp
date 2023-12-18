package com.example.dietapp.Views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavController) {
    var name by remember { mutableStateOf("Kacper") }
    var surname by remember { mutableStateOf("Berk") }
    var age by remember { mutableStateOf("23") }
    var target by remember { mutableStateOf("Zbudować masę mięśniową") }
    var isEditing by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Pasek górny z ikoną przekierowującą do aktywności profil
        TopAppBar(
            title = { },
            actions = {
                IconButton(
                    onClick = {
                        isEditing = !isEditing
                        // Ukrycie klawiatury po przełączeniu na tryb odczytu
                        if (!isEditing) {
                            keyboardController?.hide()
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                        contentDescription = if (isEditing) "Zapisz" else "Edytuj"
                    )
                }
            },

        )

        Spacer(modifier = Modifier.height(16.dp))

        // Pole tekstowe dla imienia
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Imię") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            enabled = isEditing,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        // Pole tekstowe dla nazwiska
        OutlinedTextField(
            value = surname,
            onValueChange = { surname = it },
            label = { Text("Nazwisko") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            enabled = isEditing,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        // Pole tekstowe dla wieku
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Wiek") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            enabled = isEditing,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )

        // Pole tekstowe dla celu
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text("Cel") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            enabled = isEditing,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )

        // Wyświetlanie aktualnych danych
        Spacer(modifier = Modifier.height(16.dp))
        Text("Imię: $name", fontWeight = FontWeight.Bold)
        Text("Nazwisko: $surname", fontWeight = FontWeight.Bold)
        Text("Wiek: $age", fontWeight = FontWeight.Bold)
        Text("Cel: $target", fontWeight = FontWeight.Bold)
    }
}