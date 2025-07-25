package com.example.am_projekt.ui.screens

import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.am_projekt.viewmodels.PlaceViewModel

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.pager.HorizontalPager

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale


import androidx.compose.ui.res.painterResource
import androidx.compose.ui.util.lerp

import androidx.navigation.NavController
import coil.compose.rememberImagePainter

import kotlin.math.absoluteValue


@Composable
fun PlaceDetailScreen(
    viewModel: PlaceViewModel = viewModel(),
    navController: NavController,
    placeId: Int
) {

    // Pobieranie danych miejsca z bazy
    LaunchedEffect(placeId) {
        viewModel.getPlaceById(placeId)
    }

    val place by viewModel.selectedPlace.collectAsState()
    // Uri 1 photo
    val previewUri = place?.photoUri
        ?.split(",")
        ?.firstOrNull()
        ?.takeIf { it.isNotBlank() }
        ?.let { Uri.parse(it) }

    Scaffold(
        topBar = {
            PlaceDetailTopBar(
                navController,
                placeId
            )
        },
        content = { paddingValues ->
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFE6F9E6)),
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    if (place != null) {
                    Text(text = place!!.name, style = MaterialTheme.typography.headlineMedium)
                    Text(text = "${place!!.location}")
                        Image(
                            painter = rememberImagePainter(previewUri),
                            contentDescription = "Preview Image",
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .clip(RoundedCornerShape(32.dp))
                        )

                    Text(text = "Ocena: ${place!!.rating}/10")
                    Text(text = "${place!!.description}")

                        var UriList = emptyList<Uri>()
                        if (place!!.photoUri !=null){
                            val UriList_str = place!!.photoUri!!.split(",")
                            UriList = UriList_str.map({Uri.parse(it)})
                        }

                        val pagerState = rememberPagerState(pageCount = {
                            UriList.size
                        })

                        HorizontalPager(state = pagerState, contentPadding = PaddingValues(horizontal = 64.dp), pageSpacing = 16.dp) { page ->
                            Card(
                                Modifier
                                    .size(300.dp)
                                    .graphicsLayer
                                    {
                                        val pageOffset = (
                                                (pagerState.currentPage - page) + pagerState
                                                    .currentPageOffsetFraction
                                                ).absoluteValue

                                        val scale = lerp(
                                            start = 0.85f,
                                            stop = 1f,
                                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                        )
                                        scaleX = scale
                                        scaleY = scale
                                    }
                            ) {
                                Image(
                                    painter = rememberImagePainter(UriList[page]),
                                    contentScale = ContentScale.FillWidth,
                                    contentDescription = "Photo",
                                    modifier = Modifier.fillMaxWidth()
                                )

                            }
                        }

            } else {
                Text("Loading...", modifier = Modifier.padding(16.dp))
            }}
        })
        }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailTopBar(navController: NavController, placeId: Int) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Moje miejsca",
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
            IconButton(onClick = { navController.navigate("view_places" + "/$placeId" + "/edit") }){
                Icon(
                    painter = painterResource(id = com.example.am_projekt.R.drawable.edit),
                    contentDescription = "Edit place",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

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