package com.example.am_projekt.screens
import com.example.am_projekt.utils.updateLocale
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.am_projekt.R
import com.example.am_projekt.data.dataStore
import com.example.am_projekt.data.LANGUAGE_KEY
import com.example.am_projekt.viewmodels.PlaceViewModel
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.edit
import com.example.am_projekt.Muzyczka
import com.example.am_projekt.data.SettingsDataStore


@Composable
fun SettingsScreen(
    navController: NavController,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    viewModel: PlaceViewModel = viewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val scope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { SettingsTopBar(navController = navController, isDarkTheme = isDarkTheme) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tryb ciemny
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.DarkMode),
                    style = MaterialTheme.typography.bodyLarge.copy(color = if (isDarkTheme) Color.White else Color.Black)
                )
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { onThemeChange(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.Black,
                        checkedTrackColor = Color(0xCCCCCCCC),
                        uncheckedTrackColor = Color(0xFFB0BEC5)
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Czyszczenie danych
            Spacer(modifier = Modifier.height(25.dp))
            Divider(color = if (isDarkTheme) Color.White else Color.Black, thickness = 2.dp)
            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = stringResource(id = R.string.ClearData),
                style = MaterialTheme.typography.bodyLarge.copy(color = if (isDarkTheme) Color.White else Color.Black),
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .clickable { viewModel.deleteAllPlaces() }
            )

            Spacer(modifier = Modifier.height(25.dp))
            Divider(color = if (isDarkTheme) Color.White else Color.Black, thickness = 2.dp)
            Spacer(modifier = Modifier.height(25.dp))

            LanguageDropdownMenu(isDarkTheme)

            Spacer(modifier = Modifier.height(25.dp))
            Divider(color = if (isDarkTheme) Color.White else Color.Black, thickness = 2.dp)
            Spacer(modifier = Modifier.height(25.dp))

            val volumeFlow = SettingsDataStore.volumeFlow(context).collectAsState(initial = 1.0f)

            var sliderVolume by remember { mutableStateOf(volumeFlow.value)}
            val volume by SettingsDataStore.volumeFlow(context).collectAsState(initial = 1.0f)
            LaunchedEffect(volume) {
                sliderVolume = volume
            }

            Text(text = stringResource(R.string.Volume))

            Slider(
                value = sliderVolume,
                onValueChange = {
                    sliderVolume = it
                    (context.applicationContext as? Muzyczka)?.setVolume(it)
                    scope.launch {
                        SettingsDataStore.saveVolume(context, it)
                    }
                },
                valueRange = 0f..1f
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar(navController: NavController, isDarkTheme: Boolean) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.Settings),
                    style = MaterialTheme.typography.bodyLarge.copy(color = if (isDarkTheme) Color.White else Color.Black)
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
                    contentDescription = "Opcje",
                    tint = if (isDarkTheme) Color.White else Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = if (isDarkTheme) Color.Black else Color.White
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDropdownMenu(isDarkTheme: Boolean) {
    val context = LocalContext.current
    val activity = context as? Activity
    val scope = rememberCoroutineScope()

    val languages = listOf("pl", "en")
    val languageLabels = mapOf("pl" to "Polski", "en" to "English")
    val flagIcons = mapOf(
        "pl" to R.drawable.flag_pl,
        "en" to R.drawable.flag_gb
    )

    var expanded by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            Row(
                modifier = Modifier
                    .menuAnchor()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { expanded = true }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.language),
                    contentDescription = null,
                    tint = if (isDarkTheme) Color.White else Color.Black,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.Language),
                    style = MaterialTheme.typography.bodyLarge.copy(color = if (isDarkTheme) Color.White else Color.Black)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.arrow_drop_down),
                    contentDescription = null,
                    tint = if (isDarkTheme) Color.White else Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                ExposedDropdownMenu(
                    expanded = true,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    languages.forEach { langCode ->
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    flagIcons[langCode]?.let { flagRes ->
                                        Image(
                                            painter = painterResource(id = flagRes),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(24.dp)
                                                .padding(end = 8.dp)
                                        )
                                    }
                                    Text(languageLabels[langCode] ?: langCode)
                                }
                            },
                            onClick = {
                                expanded = false
                                scope.launch {
                                    context.dataStore.edit { prefs ->
                                        prefs[LANGUAGE_KEY] = langCode
                                    }
                                    updateLocale(context, langCode)
                                    activity?.recreate()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
