package com.example.dietapp.Views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit,  // Funkcja, która będzie wywołana po naciśnięciu przycisku "Zaloguj"
    navController: NavController  // Kontroler nawigacji do obsługi przechodzenia między ekranami
) {
    // Lokalne stany dla pola email i hasła
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current  // Pobranie lokalnego kontekstu Androida

    // Komponent kolumny, zawierający pola tekstowe i przyciski
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Pole tekstowe dla wprowadzania adresu email
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
            visualTransformation = PasswordVisualTransformation(),  // Ukrywanie wprowadzanych znaków
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Default
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onLogin(email, password)  // Wywołanie funkcji logowania po naciśnięciu "Done" na klawiaturze
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Odstęp pomiędzy polami tekstowymi a przyciskami
        Spacer(modifier = Modifier.height(16.dp))

        // Przycisk do logowania
        Button(
            onClick = {
                // Sprawdzenie czy pola email i hasło nie są puste, a następnie wywołanie funkcji logowania
                if (email.isNotBlank() && password.isNotBlank()) onLogin(email, password)
                else {
                    // Wyświetlenie komunikatu o błędzie w przypadku pustych pól
                    Toast.makeText(
                        context,
                        "Login failed: ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Zaloguj")
        }

        // Odstęp pomiędzy przyciskiem logowania a przyciskiem rejestracji
        Spacer(modifier = Modifier.height(16.dp))

        // Przycisk do przechodzenia do ekranu rejestracji
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = { navController.navigate("registration") },  // Nawigacja do ekranu rejestracji
        ) {
            Text("Zarejestruj")
        }
    }
}