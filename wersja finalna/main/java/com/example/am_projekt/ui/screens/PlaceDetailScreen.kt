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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.am_projekt.R
import kotlin.math.absoluteValue
import androidx.compose.foundation.lazy.LazyColumn

@Composable
fun PlaceDetailScreen(
    viewModel: PlaceViewModel = viewModel(),
    navController: NavController,
    isDarkTheme: Boolean,
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
                placeId,
                isDarkTheme
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(16.dp)
            ) {
                if (place != null) {
                    item {
                        Text(
                            text = place!!.name,
                            style = MaterialTheme.typography.headlineMedium,
                            //modifier = Modifier.fillMaxWidth(),
                        )
                        Text(
                            text = "${place!!.location}",
                            //modifier = Modifier.fillMaxWidth()
                        )
                        Image(
                            painter = rememberImagePainter(previewUri),
                            contentDescription = "Preview Image",
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .clip(RoundedCornerShape(32.dp))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "${stringResource(id = R.string.Rating)} ${place!!.rating}/10")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "${place!!.description}")
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    val uriList = place!!.photoUri
                        ?.split(",")
                        ?.map { Uri.parse(it) }
                        ?: emptyList()

                    if (uriList.isNotEmpty()) {
                        item {
                            val pagerState = rememberPagerState(pageCount = { uriList.size })

                            HorizontalPager(
                                state = pagerState,
                                contentPadding = PaddingValues(horizontal = 64.dp),
                                pageSpacing = 16.dp
                            ) { page ->
                                Card(
                                    modifier = Modifier
                                        .size(300.dp)
                                        .graphicsLayer {
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
                                        painter = rememberImagePainter(uriList[page]),
                                        contentScale = ContentScale.FillWidth,
                                        contentDescription = "Photo",
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                } else {
                    item {
                        Text(text = stringResource(R.string.NoPlaces), modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailTopBar(navController: NavController, placeId: Int, isDarkTheme: Boolean) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.Oogabooga),
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
            IconButton(onClick = { navController.navigate("view_places" + "/$placeId" + "/edit") }){
                Icon(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "Edit place",
                    tint = if (isDarkTheme) Color.White else Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(onClick = {
                navController.navigate("start") {
                    popUpTo("start") { inclusive = true }
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Dodawanie Miejsca",
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