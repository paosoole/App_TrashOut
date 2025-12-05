package cl.trashout.ev2_phonetruck.ui.components.buttoms

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RecordToggle(isRecording: Boolean, onToggle: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onToggle,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFB9E5E8)
        )
    ) {
        if (isRecording) {
            Icon(Icons.Default.Stop, contentDescription = "Stop")
            Spacer(Modifier.width(8.dp))
            Text("Detener Ruta")
        } else {
            Icon(Icons.Default.FiberManualRecord, contentDescription = "Start")
            Spacer(Modifier.width(8.dp))
            Text("Iniciar Ruta")
        }
    }
}
