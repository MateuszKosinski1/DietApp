package com.example.dietapp.Views

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dietapp.Databases.Product
import com.google.firebase.Firebase
import com.google.firebase.database.*

@Composable
fun FoodScreen(navController: NavController) {
    // Stan komponentu: lista produktów
    var productList by remember { mutableStateOf(emptyList<Product>()) }

    // Pobieranie listy produktów z bazy danych Firebase
    val database = Firebase.database
    val myRef = database.getReference("products")
    val totalCalories = productList.sumBy { it.calories?.toInt() ?: 0 }

    // Nasłuchiwanie zmian w bazie danych i aktualizacja listy produktów
    myRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val products = dataSnapshot.children.mapNotNull { it.getValue(Product::class.java) }
            productList = products
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value.", error.toException())
        }
    })

    // Komponent kolumny, zawierający przycisk nawigacyjny i informacje o kaloriach
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            navController.navigate("main")
        }) {
            Text(text = "Strona Główna")
        }

        // Wyświetlanie sumy dostarczonych kalorii
        Text(
            text = "Suma dostarczonych kalorii: $totalCalories",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Wyświetlanie produktów za pomocą komponentu LazyColumn
        ViewProducts(productList)
    }
}

// Komponent wyświetlający listę produktów w LazyColumn
@Composable
fun ViewProducts(productList: List<Product>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(productList) { product ->
            // Wyświetlanie pojedynczego produktu za pomocą komponentu ProductItem
            ProductItem(product = product)
        }
    }
}

// Komponent reprezentujący pojedynczy produkt w formie karty
@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        // Kolumna wyświetlająca szczegóły produktu
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Nazwa: ${product.name}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Kalorie: ${product.calories}")
            Text(text = "Tłuszcze: ${product.fat}")
            Text(text = "Białko: ${product.protein}")
            Text(text = "Węglowodany: ${product.carbohydrates}")
        }
    }
}