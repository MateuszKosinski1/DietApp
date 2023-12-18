package com.example.dietapp

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.*

import com.example.dietapp.Views.ActivitiesScreen
import com.example.dietapp.Views.AddActivity
import com.example.dietapp.Views.BMICalcScreen
import com.example.dietapp.Views.CalendarScreen
import com.example.dietapp.Views.DietScreen
import com.example.dietapp.Views.FoodScreen
import com.example.dietapp.Views.InspiringGallery
import com.example.dietapp.Views.LoginScreen
import com.example.dietapp.Views.MainScreen
import com.example.dietapp.Views.MealDiary
import com.example.dietapp.Views.Profile
import com.example.dietapp.Views.RegistryScreen
import com.example.dietapp.Views.ViewActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                //Uruchomienie apliacji
                DietApp()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DietApp() {

    val navController = rememberNavController()
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()


    NavHost(
        navController = navController,
        startDestination = "loginScreen" //Wybór ekranu któru uruchamia się po starcie aplikacji
    ) {
        //Przypisanie oknom navigacji
        composable("main") {
            MainScreen(navController = navController)
        }
        composable("profile") {
            Profile(navController = navController)
        }
        composable("zmierzbmi") {
            BMICalcScreen(navController = navController)
        }
        composable("kalendarz") {
            CalendarScreen(navController = navController)
        }
        composable("zadbajodietę") {
            DietScreen(navController = navController)
        }
        composable("przejdźdodziennikaaktywności") {
            ActivitiesScreen(navController = navController)
        }
        composable("zainspirujsię") {
            InspiringGallery(navController = navController)
        }
        composable("viewactivities") {
            ViewActivity(navController = navController)
        }
        composable("addactivity") {
            AddActivity(navController = navController)
        }
        composable("loginScreen") {
            // Ekran logowania
            LoginScreen(
                navController = navController,
                onLogin = { email, password ->
                    auth.signInWithEmailAndPassword(email, password) //autoryzacja za pomocą funkcji firebase
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Login successful!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // Przejdź do ekranu DietApp po pomyślnym zalogowaniu
                                navController.navigate("main")
                            } else {
                                navController.navigate("loginScreen")
                                Toast.makeText(
                                    context,
                                    "Login failed: ${task.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            )
        }
        composable("registration") {
            RegistryScreen(navController = navController)

        }
        composable("mealdiary") {
            MealDiary(navController = navController)

        }
        composable("dailyconsumption") {
            FoodScreen(navController = navController)

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DietApp()
}

