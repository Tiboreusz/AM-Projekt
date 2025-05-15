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
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController

import coil.compose.rememberImagePainter


@Composable
fun EditPlaceScreen(
    onPlaceEdited: () -> Unit,
    viewModel: PlaceViewModel = viewModel(),
    navController: NavController,
    placeId: Int
) {

    LaunchedEffect(placeId) {
        viewModel.getPlaceById(placeId)
    }

    val place by viewModel.selectedPlace.collectAsState()

    if (place == null) {
        // Pokazuje ładowania ekranu
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        //### Zmienne na dane, które zostanąwprowadzone ###//
        var name by remember { mutableStateOf("${place!!.name}") }
        var location by remember { mutableStateOf("${place!!.location}") }
        var rating by remember { mutableStateOf("${place!!.rating}") }
        var description by remember { mutableStateOf("${place!!.description}") }
        var selectedPhotosUris by remember { mutableStateOf<List<Uri>>(emptyList()) }




    val context = LocalContext.current

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            selectedPhotosUris = uris
            uris.forEach { uri ->
                try {
                    context.contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (e: SecurityException) {

                }
            }
        }
    )




    Scaffold(
        topBar= {EditPlaceTopBar(navController)},
        content = { PaddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PaddingValues)
                    .background(Color(0xFFE6F9E6))
            ) {
                //### Nazwa miejsca ###//
                name.let {
                    OutlinedTextField(
                        value = it,
                        onValueChange = { name = it },
                        label = { Text("Nazwa miejsca") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                //### Lokalizacja ###//
                location.let {
                    OutlinedTextField(
                        value = it,
                        onValueChange = { location = it },
                        label = { Text("Lokalizacja") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                //### Ocena 3##//
                rating.let{OutlinedTextField(
                    value = it,
                    onValueChange = { rating = it },
                    label = { Text("Ocena (0-10)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )}

                Spacer(modifier = Modifier.height(8.dp))

                //### Opis ###//
                description.let {
                    OutlinedTextField(
                        value = it,
                        onValueChange = { description = it },
                        label = { Text("Opis") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                //### Przycisk Launcher dodawanie zdjęć ###//

                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    multiplePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }){
                    Text("Pick multiple photo")
                }

                Spacer(modifier = Modifier.height(16.dp))


                LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                    items(selectedPhotosUris) { uri ->
                        Image(
                            painter = rememberImagePainter(uri),
                            contentScale = ContentScale.FillWidth,
                            contentDescription = "Photo",
                            modifier = Modifier
                                .padding(16.dp, 8.dp)
                                .size(128.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                //### Przycisk do zapisu ###//
                Button(
                    onClick = {
                        val eddited_place = Place(
                            id = placeId,
                            name = name,
                            location = location,
                            rating = rating.toIntOrNull() ?: 0,
                            description = description,
                            photoUri = selectedPhotosUris.joinToString(",") { it.toString() } // do dodania :)
                        )
                        viewModel.updatePlace(eddited_place)
                        onPlaceEdited()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Edit place")
                }

            }
        }
    )
}}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPlaceTopBar(navController: NavController) {
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