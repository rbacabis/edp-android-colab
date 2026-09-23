package edu.liceo.fieldkit.ui

import android.Manifest
import android.util.Log
import androidx.camera.core.Camera
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.liceo.fieldkit.hardware.*
import edu.liceo.fieldkit.permissions.PermStatus
import edu.liceo.fieldkit.permissions.PermissionState
import edu.liceo.fieldkit.permissions.rememberPermission
import edu.liceo.fieldkit.ui.theme.LiceoFieldKitTheme
import java.io.File

@Composable
fun CameraCard(
    cameraState: PermissionState = rememberPermission(Manifest.permission.CAMERA)
) {
    val context = LocalContext.current
    val capture = remember { ImageCapture.Builder().build() }
    var photo by remember { mutableStateOf<File?>(null) }

    var cam by remember { mutableStateOf<Camera?>(null) }
    var torchOn by remember { mutableStateOf(false) }

    Card(Modifier.fillMaxWidth()) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Field photo", style = MaterialTheme.typography.titleMedium)
            PermissionGate(
                state = cameraState,
                feature = "Camera",
                reason = "We need the camera to photograph the issue you report."
            ) {
                val shake = rememberAccelerometer()
                var lastShot by remember { mutableLongStateOf(0L) }
                LaunchedEffect(shake) {
                    val now = System.currentTimeMillis()
                    if (isShake(shake) && now - lastShot > 1500) {
                        lastShot = now
                        takePhoto(context, capture) { photo = it }
                        context.buzz()
                        Log.d("FieldKit", "Shake capture")
                    }
                }

                // TODO 9a: CameraPreview for capture, full width, 240.dp tall
                CameraPreview(
                    capture = capture,
                    modifier = Modifier.fillMaxWidth().height(240.dp)
                ) { cam = it }

                // TODO 14a-14c: Flashlight button using open camera
                if (cam?.cameraInfo?.hasFlashUnit() == true) {
                    Button(onClick = {
                        torchOn = !torchOn
                        cam?.cameraControl?.enableTorch(torchOn)
                    }) {
                        Text(if (torchOn) "Torch off" else "Torch on")
                    }
                }

                // TODO 9b: Button "Take photo"
                Button(onClick = {
                    takePhoto(context, capture) { saved -> photo = saved }
                }) {
                    Text("Take photo")
                }

                // TODO 9c: Text showing the saved file name, only when photo is not null
                photo?.let {
                    Text("Saved: ${it.name}")
                }
            }

            // GIVEN (read it, do not change it): thumbnail of the last photo
            photo?.let { f ->
                val thumb = remember(f) { loadThumb(f) }
                thumb?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "Last photo",
                        modifier = Modifier.size(96.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "02-rationale")
@Composable
fun CameraCardRationalePreview() {
    LiceoFieldKitTheme {
        CameraCard(
            cameraState = PermissionState(PermStatus.NeedsRationale) {}
        )
    }
}

@Preview(showBackground = true, name = "03-open-settings")
@Composable
fun CameraCardOpenSettingsPreview() {
    LiceoFieldKitTheme {
        CameraCard(
            cameraState = PermissionState(PermStatus.Denied) {}
        )
    }
}
