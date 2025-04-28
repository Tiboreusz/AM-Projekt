import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.clickable
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults

@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = { SettingsTopBar(navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFE6F9E6))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //### Dark Mode ###//
            var isDarkMode by remember { mutableStateOf(false) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Tryb ciemny", style = MaterialTheme.typography.bodyLarge.copy(color=Color.Black))
                //Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { isDarkMode = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFFBBFFBB),
                        uncheckedThumbColor = Color(0xFF000000),
                        checkedTrackColor = Color(0xFFAADDAA),
                        uncheckedTrackColor = Color(0xFFB0BEC5)
                    ),
                    modifier = Modifier.padding(16.dp)
                )

            }

            //### Czyszczenie danych ###//
            Spacer(modifier = Modifier.height(25.dp))
            Divider(color = Color.Black, thickness = 2.dp)
            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Wyczyść dane",
                style = MaterialTheme.typography.bodyLarge.copy(color=Color.Black),
                modifier = Modifier
                    .padding(vertical = 20.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))
            Divider(color = Color.Black, thickness = 2.dp)
            Spacer(modifier = Modifier.height(25.dp))

            //### Wybór Języka ###//
            var expanded by remember { mutableStateOf(false) }
            var selectedLanguage by remember { mutableStateOf("Polski") }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .padding(vertical = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = com.example.am_projekt.R.drawable.language),
                    contentDescription = "Ikona języka",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Wybierz język",
                    style = MaterialTheme.typography.bodyLarge.copy(color=Color.Black)
                )
                Icon(
                    painter = painterResource(id = com.example.am_projekt.R.drawable.arrow_drop_down),
                    contentDescription = "Rozwiń menu",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOf("Polski", "English").forEach { language ->
                    DropdownMenuItem(
                        text = { Text(text = language) },
                        onClick = {
                            selectedLanguage = language
                            expanded = false
                        }
                    )
                }
            }

            /*Text(
                text = "Wybrany język: $selectedLanguage",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )*/
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar(navController: NavController) {
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
                    contentDescription = "Opcje",
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