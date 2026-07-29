package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


private val LightColors = lightColorScheme(
    primary = Color(0xFF771C1B),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFE9C9C8),
    secondary = Color(0xFF9E4744),
    surface = Color(0xFFFFFBFF),
    onSurfaceVariant = Color(0xFF5A4D4C)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFE0A3A0),
    onPrimary = Color(0xFF511313),
    primaryContainer = Color(0xFF651817),
    secondary = Color(0xFFD49B99),
    surface = Color(0xFF1A1110),
    onSurfaceVariant = Color(0xFFC9B8B7)
)

@Composable
fun ProfileTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp)),
        content = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    val isDark = isSystemInDarkTheme()
    

    val backgroundBrush = if (isDark) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF2D0B0A),
                Color(0xFF120504)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFFF5F5),
                Color(0xFFF7E2E1)
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(backgroundBrush)) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(bottomStart = 160.dp, bottomEnd = 160.dp)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.height(60.dp))


            Box(
                modifier = Modifier
                    .size(140.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = CircleShape
                    )
                    .padding(8.dp)
                    .background(
                        Brush.sweepGradient(
                            listOf(MaterialTheme.colorScheme.primary, Color.White, MaterialTheme.colorScheme.primary)
                        ),
                        CircleShape
                    )
                    .padding(3.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(2.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.picnirr),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Rain Robert Bacabis",
                style = MaterialTheme.typography.headlineSmall,
                color = if (isDark) MaterialTheme.colorScheme.primary else Color.White,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "BSIT 3-2",
                style = MaterialTheme.typography.titleMedium,
                color = if (isDark) MaterialTheme.colorScheme.onSurfaceVariant else Color.White.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(40.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    InfoRow(
                        icon = Icons.Default.Person,
                        label = "Full Name",
                        value = "Rain Robert Bacabis"
                    )
                    InfoRow(
                        icon = Icons.Default.School,
                        label = "Course",
                        value = "BSIT"
                    )
                    InfoRow(
                        icon = Icons.Default.Class,
                        label = "Section",
                        value = "BSIT 3-2"
                    )
                    InfoRow(
                        icon = Icons.Default.Phone,
                        label = "Mobile Number",
                        value = "+63 992 213 9708"
                    )
                    InfoRow(
                        icon = Icons.Default.Email,
                        label = "Email Address",
                        value = "rbacabis24725@liceo.edu.ph"
                    )
                }
            }
        }
    }
}


@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Preview(showBackground = true, name = "Profile - Light")
@Composable
fun ProfilePreview() {
    ProfileTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            ProfileScreen()
        }
    }
}

@Preview(showBackground = true, name = "Profile - Dark")
@Composable
fun ProfilePreviewDark() {
    ProfileTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            ProfileScreen()
        }
    }
}
