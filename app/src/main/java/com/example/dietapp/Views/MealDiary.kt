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
import com.example.dietapp.Databases.Product
import com.google.firebase.Firebase
import com.google.firebase.database.database


@Composable
fun MealDiary(navController: NavController) {
    // Lokalne stany dla wprowadzanych informacji o produkcie
    var productName by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var carbohydrates by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Co dziś jadłeś")

        // Przycisk nawigacji do głównego ekranu
        Button(onClick = {
            navController.navigate("main")
        }) {
            Text(text = "Główny ekran")
        }

        // Pola tekstowe do wprowadzania informacji o produkcie
        TextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text("Nazwa produktu") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        TextField(
            value = calories,
            onValueChange = { calories = it },
            label = { Text("Kalorie") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        TextField(
            value = protein,
            onValueChange = { protein = it },
            label = { Text("Białko") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        TextField(
            value = fat,
            onValueChange = { fat = it },
            label = { Text("Tłuszcze") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        TextField(
            value = carbohydrates,
            onValueChange = { carbohydrates = it },
            label = { Text("Węglowodany") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Przycisk dodawania produktu
        Button(
            onClick = {
                // Wywołanie funkcji dodającej produkt
                addProduct(productName, calories, fat, protein, carbohydrates)
                // Zresetowanie wartości pól po dodaniu produktu
                productName = ""
                calories = ""
                fat = ""
                protein = ""
                carbohydrates = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Dodaj Produkt")
        }
    }
}

// Funkcja dodająca produkt do bazy danych Firebase
private fun addProduct(
    productName: String,
    calories: String,
    fat: String,
    protein: String,
    carbohydrates: String
) {
    val database = Firebase.database
    val myRef = database.getReference("products")

    // Sprawdzenie, czy pola nie są puste
    if (productName.isNotBlank() && calories.isNotBlank() && fat.isNotBlank() &&
        protein.isNotBlank() && carbohydrates.isNotBlank()
    ) {
        // Utworzenie obiektu produktu
        val product = Product(
            productName,
            calories,
            fat,
            protein,
            carbohydrates
        )

        // Dodanie produktu do bazy danych
        val productId = myRef.push().key
        if (productId != null) {
            myRef.child(productId).setValue(product)
            // Opcjonalnie: Możesz również dodać obsługę powrotu do poprzedniej aktywności lub wyświetlenie komunikatu o sukcesie
        }
    }
}