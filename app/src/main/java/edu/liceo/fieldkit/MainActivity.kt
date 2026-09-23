package edu.liceo.fieldkit

// GIVEN (read it, do not change it)
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.liceo.fieldkit.ui.CameraCard
import edu.liceo.fieldkit.ui.LevelCard
import edu.liceo.fieldkit.ui.LocationCard
import edu.liceo.fieldkit.ui.theme.LiceoFieldKitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiceoFieldKitTheme {
                Scaffold { inner ->
                    Column(
                        Modifier
                            .padding(inner)
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            "LiceoFieldKit",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        LevelCard()
                        CameraCard()
                        LocationCard()
                    }
                }
            }
        }
    }
}
