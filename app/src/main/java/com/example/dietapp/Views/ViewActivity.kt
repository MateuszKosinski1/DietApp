package com.example.dietapp.Views

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

@Composable
fun ViewActivity(navController: NavController) {
    // Stan przechowujący listę aktywności
    var activityList by remember { mutableStateOf(emptyList<Activity>()) }

    // Pobieranie listy aktywności z bazy danych Firebase
    val database = Firebase.database
    val myRef = database.getReference("activities")
    val totalCalories = activityList.sumBy { it.caloriesBurned?.toInt() ?: 0 }

    // Dodanie nasłuchiwacza na zmiany w bazie danych Firebase
    myRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Aktualizacja listy aktywności po zmianie danych
            val activities = dataSnapshot.children.mapNotNull { it.getValue(Activity::class.java) }
            activityList = activities
        }

        override fun onCancelled(error: DatabaseError) {
            // Obsługa błędów odczytu danych z bazy danych
            Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
        }
    })

    // Interfejs użytkownika dla widoku aktywności
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Przycisk nawigacyjny do ekranu głównego
        Button(onClick = {
            navController.navigate("main")
        }) {
            Text(text = "Strona Główna")
        }

        // Wyświetlenie sumy spalonych kalorii
        Text(
            text = "Suma spalonych kalorii: $totalCalories",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Wyświetlenie listy aktywności
        ViewActivities(activityList)
    }
}

@Composable
fun ViewActivities(activityList: List<Activity>) {
    // Komponent LazyColumn do efektywnego wyświetlania listy
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Wykorzystanie funkcji items do wyświetlania każdej aktywności
        items(activityList) { activity ->
            activityItem(activity = activity)
        }
    }
}

@Composable
fun activityItem(activity: Activity) {
    // Komponent Card do stylizacji pojedynczej aktywności
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        // Kolumna z informacjami o aktywności
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Wyświetlenie nazwy aktywności
            Text(text = "Nazwa: ${activity.name}")
            Spacer(modifier = Modifier.height(8.dp))
            // Wyświetlenie spalonych kalorii
            Text(text = "Kalorie: ${activity.caloriesBurned}")
        }
    }
}