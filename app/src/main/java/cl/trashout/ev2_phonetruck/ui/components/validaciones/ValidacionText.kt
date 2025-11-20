package cl.trashout.ev2_phonetruck.ui.components.validaciones


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ValidacionText(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    isValid: Boolean,
    errorMessage: String = ""
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            label = { Text(label) },
            isError = !isValid && value.isNotEmpty(),
            trailingIcon = {
                when {
                    value.isEmpty() -> {}
                    isValid -> Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF4CAF50))
                    else -> Icon(Icons.Default.Error, null, tint = Color.Red)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (!isValid && value.isNotEmpty()) {
            Text(errorMessage, color = Color.Red)
        }
    }
}
