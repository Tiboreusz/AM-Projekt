package com.example.am_projekt.ui.screens

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
fun PlaceDetailScreen(
    viewModel: PlaceViewModel = viewModel(),
    navController: NavController,
    placeId: Int
) {
    LaunchedEffect(placeId) {
        viewModel.getPlaceById(placeId)
    }

    val place by viewModel.selectedPlace.collectAsState()

    if (place != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = place!!.name, style = MaterialTheme.typography.headlineMedium)
            Text(text = "Location: ${place!!.location}")
            Text(text = "Rating: ${place!!.rating}/10")
            // Add more UI elements as needed
        }
    } else {
        Text("Loading...", modifier = Modifier.padding(16.dp))
    }
}
