package cl.trashout.ev2_phonetruck.ui.components.buttoms

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalDensity
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun RecordToggle_2(
    isRecording: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val width = 120.dp
    val height = 32.dp
    val knobSize = 28.dp

    val widthPx = with(LocalDensity.current) { width.toPx() }
    val knobSizePx = with(LocalDensity.current) { knobSize.toPx() }
    val maxOffset = widthPx - knobSizePx

    var offset by remember { mutableStateOf(if (isRecording) maxOffset else 0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(height / 2))
            .background(Color(0xFFB9E5E8))
    ) {

        // Texto centrado
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isRecording) "Detener" else "Iniciar",
                style = MaterialTheme.typography.labelMedium,
            )
        }

        // Botón deslizable
        Box(
            modifier = Modifier
                .offset { IntOffset(offset.roundToInt(), 0) }
                .size(knobSize)
                .clip(RoundedCornerShape(height / 2))
                .background(Color.White)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        offset = (offset + delta).coerceIn(0f, maxOffset)
                    },
                    onDragStopped = {
                        scope.launch {
                            val newIsRecording = offset > maxOffset / 2

                            offset = if (newIsRecording) maxOffset else 0f

                            // cambio de estado
                            if (newIsRecording != isRecording) {
                                onToggle()
                            }
                        }
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.FiberManualRecord,
                contentDescription = null,
                tint = if (isRecording) Color.Red else Color(0xFFAEE5EC)
            )
        }
    }
}
