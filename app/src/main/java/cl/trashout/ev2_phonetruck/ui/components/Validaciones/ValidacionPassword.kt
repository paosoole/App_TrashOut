package cl.trashout.ev2_phonetruck.ui.components.Validaciones

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
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun ValidacionPassword(
    password: String,
    onPasswordChange: (String) -> Unit
) {
    val isValid = ValidacionUtils.isValidPassword(password)

    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = !isValid && password.isNotEmpty(),
            trailingIcon = {
                when {
                    password.isEmpty() -> {}
                    isValid -> Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF4CAF50))
                    else -> Icon(Icons.Default.Error, null, tint = Color.Red)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (!isValid && password.isNotEmpty()) {
            Text(
                "Debe tener mínimo 6 caracteres, una mayúscula, un número y un símbolo",
                color = Color.Red
            )
        }
    }
}
