import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.am_projekt.R

@Composable
fun StartScreen(navController: NavController, isDarkTheme: Boolean) {
    var showVideo by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val videoUri = Uri.parse("android.resource://${context.packageName}/${R.raw.wideo_do_spelnienia_wymagan_projektowych}")
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(150.dp)
                    .padding(16.dp)
                    .offset(x = 100.dp, y = 5.dp)
                    .clip(CircleShape)
                    .clickable { showVideo = true }
            ) {
                if (showVideo) {
                    AndroidView(
                        factory = {
                            VideoView(it).apply {
                                setVideoURI(videoUri)
                                setOnCompletionListener {
                                    showVideo = false
                                }
                                setOnPreparedListener { it.isLooping = false }
                                start()
                            }
                        },
                        modifier = Modifier
                            .matchParentSize()
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))

            Divider(color = if (isDarkTheme) Color.White else Color.Black)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clickable { navController.navigate("view_places") },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.MyPlacesText),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        color = if (isDarkTheme) Color.White else Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.pin),
                    tint = if (isDarkTheme) Color.White else Color.Black,
                    contentDescription = null,
                    modifier = Modifier.size(70.dp)
                )
            }

            Divider(color = if (isDarkTheme) Color.White else Color.Black)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clickable { navController.navigate("add_place") },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.AddPlace),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        color = if (isDarkTheme) Color.White else Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.add_button),
                    tint = if (isDarkTheme) Color.White else Color.Black,
                    contentDescription = null,
                    modifier = Modifier.size(70.dp)
                )
            }

            Divider(color = if (isDarkTheme) Color.White else Color.Black)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clickable { navController.navigate("settings") },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.Settings),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        color = if (isDarkTheme) Color.White else Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.settings),
                    tint = if (isDarkTheme) Color.White else Color.Black,
                    contentDescription = null,
                    modifier = Modifier.size(70.dp)
                )
            }

            Divider(color = if (isDarkTheme) Color.White else Color.Black)

        }
    }
}