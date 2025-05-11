package com.example.am_projekt.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.am_projekt.model.Place
import com.example.am_projekt.viewmodels.PlaceViewModel




//### Tu nie ma co komentować za bardzo, po prostu jest tworzony widok ###//

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewPlacesScreen(viewModel: PlaceViewModel = viewModel(), navController: NavController, onPlaceClick: (Int)->Unit) {
    //### Pobieranie miejsc z bazy ###//
    val places = viewModel.allPlaces.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            ViewPlacesTopBar(
                navController
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFE6F9E6))
            ) {
                if (places.value.isEmpty()) {
                    Text(text = "Brak zapisanych miejsc.", style = MaterialTheme.typography.headlineSmall)
                } else {
                    LazyColumn {
                        items(places.value) { place: Place ->
                            PlaceItem(place, onClick = onPlaceClick)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun PlaceItem(place: Place, onClick: (Int)->Unit) {
    val previewUri = place.photoUri
        ?.split(",")
        ?.firstOrNull()
        ?.takeIf { it.isNotBlank() }
        ?.let { Uri.parse(it) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable { onClick(place.id) },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFA8E6CF))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = place.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${place.location}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "${place.rating}/10")
            //Spacer(modifier = Modifier.height(4.dp))
            //Text(text = "Opis: ${place.description}")
        }
            if (previewUri != null) {
                Image(
                    painter = rememberImagePainter(previewUri),
                    contentDescription = "Preview Image",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(start = 8.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewPlacesTopBar(navController: NavController) {
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
                    contentDescription = "Twoje Miejsca",
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