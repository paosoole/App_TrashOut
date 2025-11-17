package cl.trashout.ev2_phonetruck.ui.components.validation

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
import cl.trashout.ev2_phonetruck.ui.components.Validaciones.ValidacionUtils

@Composable
fun ValidacionPassConfirm(
    password: String,
    confirmPassword: String,
    onConfirmChange: (String) -> Unit
) {
    val matches = ValidacionUtils.isMatchingPasswords(password, confirmPassword)

    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = onConfirmChange,
            label = { Text("Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = !matches && confirmPassword.isNotEmpty(),
            trailingIcon = {
                when {
                    confirmPassword.isEmpty() -> {}
                    matches -> Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF4CAF50))
                    else -> Icon(Icons.Default.Error, null, tint = Color.Red)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (!matches && confirmPassword.isNotEmpty()) {
            Text("Las contraseñas no coinciden", color = Color.Red)
        }
    }
}
