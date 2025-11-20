package cl.trashout.ev2_phonetruck.ui.components.validaciones

import androidx.compose.runtime.Composable
import cl.trashout.ev2_phonetruck.utils.ValidacionUtils

@Composable
fun ValidacionEmail(
    email: String,
    onEmailChange: (String) -> Unit
) {
    val isValid = ValidacionUtils.isValidEmail(email)

    ValidacionText(
        value = email,
        onChange = onEmailChange,
        label = "Correo electrónico",
        isValid = isValid,
        errorMessage = "Correo inválido (debe contener @ y .)"
    )
}
