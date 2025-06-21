package com.example.am_projekt.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.am_projekt.R
import com.example.am_projekt.model.Place
import com.example.am_projekt.viewmodels.PlaceViewModel




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewPlacesScreen(viewModel: PlaceViewModel = viewModel(), navController: NavController, isDarkTheme: Boolean, onPlaceClick: (Int)->Unit) {
    val places = viewModel.allPlaces.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            ViewPlacesTopBar(
                navController, isDarkTheme
            )
            Spacer(modifier = Modifier.height(8.dp))
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (places.value.isEmpty()) {
                    Text(text = stringResource(id = R.string.NoPlaces), style = MaterialTheme.typography.headlineSmall, color = if (isDarkTheme) Color.White else Color.Black)
                } else {
                    LazyColumn {
                        items(places.value) { place: Place ->
                            PlaceItem(place, isDarkTheme ,onClick = onPlaceClick)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun PlaceItem(place: Place, isDarkTheme: Boolean, onClick: (Int)->Unit) {
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
            containerColor = if (isDarkTheme) Color.DarkGray else Color.White)
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
            Text(text = place.name, style = MaterialTheme.typography.headlineSmall, color = if (isDarkTheme) Color.White else Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${place.location}", color = if (isDarkTheme) Color.White else Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "${place.rating}/10", color = if (isDarkTheme) Color.White else Color.Black)
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
fun ViewPlacesTopBar(navController: NavController, isDarkTheme: Boolean) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.MyPlacesText),
                    style = MaterialTheme.typography.titleLarge,
                    color = if (isDarkTheme) Color.White else Color.Black
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Wstecz",
                    tint = if (isDarkTheme) Color.White else Color.Black,
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
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = stringResource(id = R.string.MyPlacesText),
                    tint = if (isDarkTheme) Color.White else Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = if (isDarkTheme) Color.DarkGray else Color.White,
            titleContentColor = if (isDarkTheme) Color.White else Color.Black,
            navigationIconContentColor = if (isDarkTheme) Color.White else Color.Black,
            actionIconContentColor = if (isDarkTheme) Color.White else Color.Black
        ),
        modifier = Modifier.fillMaxWidth()
    )
}