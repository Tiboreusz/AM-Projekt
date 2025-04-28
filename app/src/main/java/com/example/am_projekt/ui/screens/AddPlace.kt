package com.example.am_projekt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.am_projekt.model.Place
import com.example.am_projekt.viewmodels.PlaceViewModel
import com.example.am_projekt.viewmodels.PlaceViewModelFactory
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController


@Composable
fun AddPlaceScreen(
    onPlaceAdded: () -> Unit,
    viewModel: PlaceViewModel = viewModel(
        factory = PlaceViewModelFactory(LocalContext.current.applicationContext as Application) //Tworzenie instancji PlaceViewModel
    ),
    navController: NavController
) {
    //### Zmienne na dane, które zostanąwprowadzone ###//
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar={AddPlaceTopBar(navController)},
        content = { PaddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PaddingValues)
                    .background(Color(0xFFE6F9E6))
            ) {
                //### Nazwa miejsca ###//
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nazwa miejsca") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                //### Lokalizacja ###//
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Lokalizacja") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                //### Ocena 3##//
                OutlinedTextField(
                    value = rating,
                    onValueChange = { rating = it },
                    label = { Text("Ocena (0-10)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))

                //### Opis ###//
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Opis") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                //### Przycisk do zapisu ###//
                Button(
                    onClick = {
                        val place = Place(
                            name = name,
                            location = location,
                            rating = rating.toIntOrNull() ?: 0,
                            description = description,
                            photoUri = "" // do dodania :)
                        )
                        viewModel.insertPlace(place)
                        onPlaceAdded()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Dodaj miejsce")
                }

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaceTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Ustawienia",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = com.example.am_projekt.R.drawable.back),
                    contentDescription = "Wstecz",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                navController.navigate("start") {
                    popUpTo("start") { inclusive = true }
                }
            }) {
                Icon(
                    painter = painterResource(id = com.example.am_projekt.R.drawable.home),
                    contentDescription = "Dodawanie Miejsca",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFA8E6CF),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth()
    )
}