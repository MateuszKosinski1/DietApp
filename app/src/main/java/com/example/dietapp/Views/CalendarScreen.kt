package com.example.dietapp.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(navController: NavController) {
    // Główny ekran kalendarza
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Kalendarz")
        // Przycisk do nawigacji do strony głównej
        Button(onClick = {
            navController.navigate("main")
        }) {
            Text(text = "Strona główna")
        }
        // Wyświetlanie kalendarza
        CalendarView()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView() {
    // Komponent wyświetlający kalendarz
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var monthToDisplay by remember { mutableStateOf(selectedDate.month) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
    ) {
        // Nagłówek kalendarza z przyciskami nawigacyjnymi
        CalendarHeader(
            month = monthToDisplay,
            onPrevMonthClick = { monthToDisplay = monthToDisplay.minus(1) },
            onNextMonthClick = { monthToDisplay = monthToDisplay.plus(1) }
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Siatka kalendarza
        CalendarGrid(
            selectedDate = selectedDate,
            monthToDisplay = monthToDisplay,
            onDateClick = { selectedDate = it }
        )
    }
}

@Composable
fun CalendarHeader(
    month: Month,
    onPrevMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit
) {
    // Nagłówek kalendarza z przyciskami nawigacyjnymi
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Przycisk nawigacyjny do poprzedniego miesiąca
        IconButton(onClick = onPrevMonthClick) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Previous Month")
        }

        // Wyświetlanie aktualnego miesiąca
        Text(
            text = month.toString(),
        )

        // Przycisk nawigacyjny do następnego miesiąca
        IconButton(onClick = onNextMonthClick) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Next Month")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(
    selectedDate: LocalDate,
    monthToDisplay: Month,
    onDateClick: (LocalDate) -> Unit
) {
    // Siatka kalendarza
    val daysInMonth = YearMonth.of(selectedDate.year, monthToDisplay).lengthOfMonth()
    val firstDayOfMonth = LocalDate.of(selectedDate.year, monthToDisplay, 1)
    val startDayOfWeek = firstDayOfMonth.dayOfWeek.value

    // Obliczenie liczby wierszy potrzebnych w siatce kalendarza
    val numRows = ((daysInMonth + startDayOfWeek - 1) / 7) + 1

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(numRows) { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .clip(shape = CircleShape),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (col in 1..7) {
                    val day = (row * 7) + col - startDayOfWeek + 1
                    val isWithinMonth = day in 1..daysInMonth

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Transparent)
                            .padding(4.dp)
                    ) {
                        if (isWithinMonth) {
                            val date = LocalDate.of(selectedDate.year, monthToDisplay, day)
                            // Wyświetlanie pojedynczego dnia w kalendarzu
                            CalendarDay(
                                date = date,
                                isSelected = date == selectedDate,
                                onDateClick = onDateClick
                            )
                        } else {
                            // Placeholder dla dni spoza zakresu miesiąca
                            Spacer(modifier = Modifier.fillMaxSize())
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun CalendarDay(
    date: LocalDate,
    isSelected: Boolean,
    onDateClick: (LocalDate) -> Unit
) {
    // Komponent reprezentujący pojedynczy dzień w kalendarzu
    var clickedDate by remember { mutableStateOf<LocalDate?>(null) }

    // Jeżeli data została kliknięta, pokazujemy AlertDialog
    if (clickedDate != null && clickedDate == date) {
        AlertDialog(
            onDismissRequest = { clickedDate = null },
            title = {
                Text("Zamówiono konsultację")
            },
            text = {
                Text("Proszę czekać na połączenie od trenera")
            },
            confirmButton = {
                Button(
                    onClick = {
                        clickedDate = null
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    // Komponent Box reprezentujący pojedynczy dzień w kalendarzu
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = if (isSelected || clickedDate == date) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = MaterialTheme.shapes.small
            )
            .clickable {
                // Zmiana stanu tylko po drugim kliknięciu w to samo miejsce
                if (clickedDate == date) {
                    clickedDate = null
                } else {
                    // Jeżeli klikamy w inne miejsce, zmieniamy aktualnie klikniętą datę
                    clickedDate = date
                    onDateClick(date)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            style = LocalTextStyle.current.copy(
                color = if (isSelected || clickedDate == date) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp
            )
        )
    }
}