package com.example.am_projekt

import androidx.lifecycle.lifecycleScope
import androidx.compose.runtime.LaunchedEffect
import com.example.am_projekt.data.DataStoreManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import com.example.am_projekt.screens.SettingsScreen
import StartScreen
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.am_projekt.database.AppDatabase
import com.example.am_projekt.database.PlaceDao
import com.example.am_projekt.screens.AddPlaceScreen
import com.example.am_projekt.ui.screens.*
import com.example.am_projekt.ui.theme.AM_ProjektTheme
import androidx.compose.runtime.remember
//import androidx.compose.ui.text.intl.Locale
import com.example.am_projekt.data.getLanguagePreferenceFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import androidx.work.Configuration
import java.util.Locale

class MainActivity : ComponentActivity() {

    private lateinit var dataStoreManager: DataStoreManager

    override fun attachBaseContext(newBase: Context) {
        val language = runBlocking {
            getLanguagePreferenceFlow(newBase).first()
        }
        val locale = Locale(language)
        val config = android.content.res.Configuration(newBase.resources.configuration)
        Locale.setDefault(locale)
        config.setLocale(locale)
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataStoreManager = DataStoreManager(applicationContext)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "places_database"
        ).build()
        val placeDao = database.placeDao()

        setContent {
            val darkThemeFlow = dataStoreManager.darkModeFlow.collectAsState(initial = false)
            var isDarkTheme by remember { mutableStateOf(darkThemeFlow.value) }

            LaunchedEffect(darkThemeFlow.value) {
                isDarkTheme = darkThemeFlow.value
            }

            AM_ProjektTheme(darkTheme = isDarkTheme) {
                App(
                    placeDao = placeDao,
                    isDarkTheme = isDarkTheme,
                    onThemeChange = { enabled ->
                        isDarkTheme = enabled
                        lifecycleScope.launch {
                            dataStoreManager.setDarkMode(enabled)
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun App(
    placeDao: PlaceDao,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "start") {
        composable("start") {
            StartScreen(navController, isDarkTheme)
        }
        composable("settings") {
            SettingsScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange
            )
        }
        composable("add_place") {
            AddPlaceScreen(onPlaceAdded = { navController.popBackStack() }, navController = navController, isDarkTheme = isDarkTheme)
        }
        composable("view_places") {
            ViewPlacesScreen(navController = navController, isDarkTheme = isDarkTheme, onPlaceClick = { navController.navigate("view_places/$it") })
        }
        composable("view_places/{placeId}") { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId")?.toIntOrNull() ?: 0
            PlaceDetailScreen(navController = navController, placeId = placeId, isDarkTheme = isDarkTheme)
        }
        composable("view_places/{placeId}/edit") { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId")?.toIntOrNull() ?: 0
            EditPlaceScreen(onPlaceEdited = { navController.popBackStack() }, navController = navController, placeId = placeId, isDarkTheme = isDarkTheme)
        }
    }
}


