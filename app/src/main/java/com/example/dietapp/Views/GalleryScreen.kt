package com.example.dietapp.Views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dietapp.R
import com.google.accompanist.pager.ExperimentalPagerApi


@Composable
fun InspiringGallery(navController: NavController) {
    // Komponent kolumny, centralizujący zawartość
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tekst klikalny, np. dla nawigacji do strony metamorfoz
        ClickableText(
            text = AnnotatedString("Najlepsze metamorfozy"),
            onClick = {
                // Obsługa kliknięcia, na przykład nawigacja
            }
        )
        // Przycisk do nawigacji do strony głównej
        Button(onClick = {
            navController.navigate("main")
        }) {
            Text(text = "Strona Główna")
        }
        // Wywołanie komponentu karuzeli obrazów
        CarouselActivity()
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun CarouselActivity() {
    // Lista zasobów obrazów
    val images = listOf(
        R.drawable.image0,
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6,
        R.drawable.image7
    )

    // Lista przykładowych tekstów
    val sampleTexts = listOf(

        "Odkryj inspirujący świat determinacji i siły "
        + "w naszej galerii sportowców. Zanurz się w historiach wytrwałości,"
        + " pokonywania przeszkód i osiągania nieosiągalnego. Przejrzyj galerię, "
        + "by doświadczyć mocy, jaką niesie za sobą sport. Czekają tam historie zwycięstw,"
        +" które mogą być dla Ciebie źródłem motywacji. Gotowy na dawkę pozytywnej energii?",

        "Spotkaj Joego, niezwyciężonego kulturystę, którego determinacja i siła to nie tylko mięśnie, ale również nieugięte podejście do życia. Jego historia to połączenie ciężkich treningów i niezłomnej woli. Gotowy na inspirację?",
        "Odkryj świat treningu razem z Michałem, kulturystą z pasją do doskonalenia ciała i umysłu. Jego determinacja i ciężka praca to recepta na sukces w każdej dziedzinie życia!",
        "Poznaj historię Roberta, kulturysty, który nie boi się wyzwań. Jego trening to nie tylko forma aktywności, to styl życia. Gotowy zmienić swoje nawyki na lepsze?",
        "Zanurz się w świecie treningu u boku Łukasza, kulturysty z determinacją w oczach. Jego siła to nie tylko efekt pracy, to także rezultat nieustannej walki o lepszą wersję samego siebie.",
        "Spotkaj Marcina, kulturystę, którego historia to inspiracja dla wszystkich marzących o transformacji. Jego trening to nie tylko sposób na budowanie mięśni, ale także umiejętność pokonywania przeciwności losu. Gotowy na rewolucję w swoim życiu?",
        "Odkryj historię Ewy, kulturystki o niezwykłej sile i gracjii. Jej trening to nie tylko pokaz siły, to także wyraz odwagi i determinacji. Gotowa na transformację?",
        "Witaj w świecie pasji i determinacji, gdzie trening to nie tylko rutyna, ale rytuał. Poznaj Annę, kulturystkę z determinacją stalowej woli, gotową zdobyć każdy cel!"
    )

    // Lokalny stan currentPage, śledzący aktualną stronę
    var currentPage by remember { mutableStateOf(0) }

    // Komponent kolumny, który zawiera karuzelę obrazów
    Column(
        modifier = Modifier
            //.fillMaxWidth()
            .padding(16.dp)
    ) {
        // Komponent pagera dla przewijania obrazów
        HorizontalPager(
            state = androidx.compose.foundation.pager.rememberPagerState { currentPage },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            // Zdjęcie
            Image(
                painter = painterResource(id = images[currentPage]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            )

            // Przykładowy tekst pod każdym zdjęciem
            Text(
                text = sampleTexts[currentPage],
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(Color.Black), // Możesz dostosować kolor tła tekstu
                color = Color.White
            )
        }

        // Tekst wyświetlający aktualnie wybrany tekst
        Text(
            text = sampleTexts[currentPage],
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.Transparent) // Możesz dostosować kolor tła tekstu
                .align(Alignment.CenterHorizontally),
            color = Color.Black
        )

        // Komponenty przycisków nawigacyjnych
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Strzałka w lewo
            IconButton(
                onClick = {
                    // Przewiń do poprzedniej strony
                    currentPage = (currentPage - 1).coerceIn(0, images.size - 1)
                },
                enabled = currentPage > 0
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }

            // Strzałka w prawo
            IconButton(
                onClick = {
                    // Przewiń do następnej strony
                    currentPage = (currentPage + 1).coerceIn(0, images.size - 1)
                },
                enabled = currentPage < images.size - 1
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}