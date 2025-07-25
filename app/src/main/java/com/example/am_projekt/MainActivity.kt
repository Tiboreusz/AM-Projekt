package com.example.am_projekt

import SettingsScreen
import StartScreen
import com.example.am_projekt.ui.screens.*
import com.example.am_projekt.database.AppDatabase
import com.example.am_projekt.database.PlaceDao
import com.example.am_projekt.screens.AddPlaceScreen
import androidx.room.Room
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.am_projekt.ui.theme.AM_projektTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //### Inicjonawnie bazy ###
            val database = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "places_database"
            ).build()

            val placeDao = database.placeDao()

            AM_projektTheme {
                App(placeDao)
            }
        }
    }
}

@Composable
fun App(placeDao: PlaceDao) {
    val navController = rememberNavController()
    //### Nawigacja ###
    NavHost(navController = navController, startDestination = "start") {
        composable("start") {
            StartScreen(navController)
        }
        composable("settings") {
            SettingsScreen(navController)
        }
        composable("add_place") {
            AddPlaceScreen(onPlaceAdded = {navController.popBackStack()}, navController = navController)
        }
        composable("view_places") {
            ViewPlacesScreen(navController = navController, onPlaceClick = {navController.navigate("view_places" + "/$it")})
        }
        composable("view_places/{placeId}"){
            backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId", "1")?.toIntOrNull() ?: 0
            PlaceDetailScreen(navController = navController, placeId = placeId)
        }
        composable("view_places/{placeId}/edit"){
                backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId", "1")?.toIntOrNull() ?: 0
            EditPlaceScreen(onPlaceEdited = {navController.popBackStack()}, navController = navController, placeId = placeId)
        }

    }
}

