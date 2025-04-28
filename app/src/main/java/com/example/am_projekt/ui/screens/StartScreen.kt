import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun StartScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC8F6C7))
            .padding(24.dp)
    ) {

        Image(
            painter = painterResource(id = com.example.am_projekt.R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .padding(16.dp)
                .offset(x = 100.dp, y= 5.dp)
        )

        Spacer(modifier = Modifier.height(100.dp))

        Divider(color = Color.Black, thickness = 2.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .clickable { navController.navigate("view_places") },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Moje miejsca",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = MaterialTheme.typography.headlineSmall.fontSize),
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = com.example.am_projekt.R.drawable.pin),
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )
        }

        Divider(color = Color.Black, thickness = 2.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .clickable { navController.navigate("add_place") },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dodaj miejsce",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = MaterialTheme.typography.headlineSmall.fontSize),
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = com.example.am_projekt.R.drawable.add_button),
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )
        }

        Divider(color = Color.Black, thickness = 2.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .clickable { navController.navigate("settings") },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ustawienia",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = MaterialTheme.typography.headlineSmall.fontSize),
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = com.example.am_projekt.R.drawable.settings),
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )
        }

        Divider(color = Color.Black, thickness = 2.dp)

    }
}