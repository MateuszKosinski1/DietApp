package com.example.dietapp.Views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth


@Composable
fun RegistryScreen(
    navController: NavController,
) {
    val auth = FirebaseAuth.getInstance() // Pobranie instancji autentykacji z Firebase
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") } // Inicjalizacja zmiennych
    val context = LocalContext.current

    // Kolumna obejmująca interfejs użytkownika rejestracji
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Pole tekstowe dla wprowadzania adresu e-mail
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Pole tekstowe dla wprowadzania hasła
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Hasło") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Obsługa zdarzenia kliknięcia przycisku Done na klawiaturze
                    if (email.isNotBlank() && password.isNotBlank()) {
                        // Rejestracja użytkownika przy użyciu adresu e-mail i hasła
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Informacja o pomyślnej rejestracji
                                    Toast.makeText(
                                        context,
                                        "Registration successful!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    // Nawigacja do ekranu logowania po udanej rejestracji
                                    navController.navigate("loginScreen")
                                } else {
                                    // Informacja o nieudanej rejestracji
                                    Toast.makeText(
                                        context,
                                        "Registration failed: ${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        // Komunikat, gdy adres e-mail i hasło są puste
                        Toast.makeText(
                            context,
                            "Email and password cannot be empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Odstęp między polami tekstowymi a przyciskami
        Spacer(modifier = Modifier.height(16.dp))

        // Rząd z przyciskami "Wstecz" i "Załóż konto"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Przycisk do nawigacji do ekranu logowania
            Button(
                onClick = { navController.navigate("loginScreen") },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text("Wstecz")
            }

            // Odstęp między przyciskami
            Spacer(modifier = Modifier.width(16.dp))

            // Przycisk do założenia konta
            Button(
                onClick = {
                    // Obsługa zdarzenia kliknięcia przycisku "Załóż konto"
                    if (email.isNotBlank() && password.isNotBlank()) {
                        // Rejestracja użytkownika przy użyciu adresu e-mail i hasła
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Informacja o pomyślnej rejestracji
                                    Toast.makeText(
                                        context,
                                        "Registration successful!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    // Nawigacja do ekranu logowania po udanej rejestracji
                                    navController.navigate("loginScreen")
                                } else {
                                    // Informacja o nieudanej rejestracji
                                    Toast.makeText(
                                        context,
                                        "Registration failed: ${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        // Komunikat, gdy adres e-mail i hasło są puste
                        Toast.makeText(
                            context,
                            "Email and password cannot be empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text("Załóż konto")
            }
        }
    }
}