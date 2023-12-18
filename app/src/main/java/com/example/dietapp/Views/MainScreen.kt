package com.example.dietapp.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    var displayedText by remember { mutableStateOf("Możesz wszystko") }

    // Dostępne teksty
    val texts = listOf(
        "Twój jedyny rywal jesteś Ty. Każdy trening to wyścig z wersją siebie sprzed wczoraj.",
        "Sukces nie przychodzi od razu. Codzienna praca nad sobą to klucz do osiągnięcia celów.",
        "Początki mogą być trudne, ale każdy krok prowadzi do transformacji. Nie rezygnuj!",
        "Siła nie tylko buduje mięśnie, ale i charakter. Bądź silny zarówno na zewnątrz, jak i wewnątrz.",
        "Trening to inwestycja w zdrowie i dobre samopoczucie. Zainwestuj w siebie codziennie.",
        "Nie szukaj wymówek, szukaj sposobów. Każdy wysiłek przynosi rezultaty.",
        "Twoje ciało osiągnie, co umysł sobie wymyśli. Wierzyć to osiągać.",
        "Zmiany zaczynają się poza strefą komfortu. Wyjdź poza nią i zobacz, co się stanie.",
        "Zjedz słabość na śniadanie, a siłę na obiad. Twój umysł decyduje, czy jesteś zwycięzcą czy nie.",
        "Czasami największy krok to ten, który zrobisz wracając do treningu po przerwie. Niech każdy dzień będzie kolejnym krokiem ku lepszej wersji siebie.")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Pasek górny z ikoną przekierowującą do aktywności profil
        TopAppBar(
            title = { },
            actions = {
                IconButton(
                    onClick = {
                        // Przekierowanie do aktywności profil
                        navController.navigate("profile")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profil"
                    )
                }
                // Przycisk wyloguj
                IconButton(
                    onClick = {
                        // Obsługa wylogowania
                        navController.navigate("loginScreen")
                        auth.signOut()
                    },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Wyloguj"
                    )
                }
            },


        )


        // Przyciski nawigacyjne
        listOf("Zmierz BMI", "Kalendarz", "Zadbaj o dietę", "Przejdź do dziennika aktywności", "Zainspiruj się")
            .forEach { text ->
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    navController.navigate(text.toLowerCase().replace(" ", ""))
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = text)
                }
            }

        // Przycisk do losowego wybierania tekstu
        Spacer(modifier = Modifier.fillMaxWidth())
        Button(onClick = { displayedText = texts.random()
        },   modifier = Modifier.fillMaxWidth()) {
            Text("Losuj tekst motywacyjny")
        }

        // Tekst wyświetlający informację
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = displayedText,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .border(1.dp, MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small)
                .padding(16.dp)
        )
    }
}