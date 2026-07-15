package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme


// Midnight & Aurora theme: Deep Navy and Neon Teal for a modern, unique look
private val Primary = Color(0xFF0F172A)
private val Accent = Color(0xFF2DD4BF)
private val AccentDark = Color(0xFF0D9488)
private val PageTop = Color(0xFFF8FAFC)
private val PageBottom = Color(0xFFE2E8F0)
private val PageTopDark = Color(0xFF020617)
private val PageBottomDark = Color(0xFF0F172A)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        BusinessCard()
                    }
                }
            }
        }
    }
}

@Composable
fun BusinessCard() {
    // Read the system theme once, then derive every color from it
    val isDark = isSystemInDarkTheme()

    val cardBackground = if (isDark) Color(0xFF111827) else Color.White
    val contentColor = if (isDark) Color.White else Primary
    val subtitleColor = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
    val dividerColor = if (isDark) Color(0xFF1F2937) else Color(0xFFE2E8F0)
    val labelColor = if (isDark) Color(0xFFE2E8F0) else Color(0xFF1E293B)

    // Name color: Solid black for light mode, solid white for dark mode
    val nameGradient = if (isDark) {
        Brush.linearGradient(listOf(Color.White, Color.White))
    } else {
        Brush.linearGradient(listOf(Color.Black, Color.Black))
    }

    val pageGradient = if (isDark) {
        Brush.verticalGradient(listOf(PageTopDark, PageBottomDark))
    } else {
        Brush.verticalGradient(listOf(PageTop, PageBottom))
    }

    // Dark mode gets a deeper, more muted version of the banner so it
    // blends with the near-black card instead of looking pasted on top
    val bannerGradient = if (isDark) {
        Brush.linearGradient(listOf(Primary, Color(0xFF1E293B), Color(0xFF020617)))
    } else {
        Brush.linearGradient(listOf(AccentDark, Accent, Color(0xFF5EEAD4)))
    }
    val glowAlpha = if (isDark) 0.22f else 0.45f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(pageGradient)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = cardBackground,
                contentColor = contentColor
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                // Diagonal gradient banner behind the photo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(
                            bannerGradient,
                            RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Soft glow behind the avatar for depth
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(Accent.copy(alpha = glowAlpha), Color.Transparent)
                                )
                            )
                    )
                    Image(
                        painter = painterResource(R.drawable.picnirr),
                        contentDescription = "Profile photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(112.dp)
                            .clip(CircleShape)
                            .border(4.dp, Color.White, CircleShape)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    "Rain Robert Bacabis",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        brush = nameGradient
                    )
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "3rd Year IT Student",
                    fontSize = 14.sp,
                    color = subtitleColor
                )

                Spacer(Modifier.height(20.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    thickness = 1.dp,
                    color = dividerColor
                )
                Spacer(Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    ContactRow(Icons.Default.Phone, "+63 992 213 9708", labelColor = labelColor)
                    ContactRow(Icons.Default.Email, "rbacabis24725@liceo.edu.ph", labelColor = labelColor)
                }

                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun ContactRow(
    icon: ImageVector,
    label: String,
    labelColor: Color = Color(0xFF3A3A3A),
    onClick: () -> Unit = {}
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(14.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        listOf(Accent.copy(alpha = 0.09f), Accent.copy(alpha = 0.015f))
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(Accent, AccentDark))),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Text(label, fontSize = 15.sp, color = labelColor)
        }
    }
}

@Preview(showBackground = true, name = "Light")
@Composable
fun BusinessCardPreview() {
    MyApplicationTheme {
        BusinessCard()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun BusinessCardPreviewDark() {
    MyApplicationTheme {
        BusinessCard()
    }
}

@Preview(showBackground = true, fontScale = 1.8f, name = "Large Font")
@Composable
fun BusinessCardPreviewLargeFont() {
    MyApplicationTheme {
        BusinessCard()
    }
}