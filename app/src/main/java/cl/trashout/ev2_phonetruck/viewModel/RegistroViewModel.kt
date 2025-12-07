import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.trashout.ev2_phonetruck.model.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegistroViewModel(
private val repository: UserRepository
) : ViewModel() {

    fun registrar(
        nombre: String,
        correo: String,
        username: String,
        password: String,
        comuna: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {

            // --------------------------
            // ✔ VALIDACIONES LOCALES
            // --------------------------
            if (nombre.isBlank() || correo.isBlank() || username.isBlank() || password.isBlank()) {
                onResult(false, "Todos los campos son obligatorios")
                return@launch
            }

            if (!isCorreoValido(correo)) {
                onResult(false, "Correo electrónico no válido")
                return@launch
            }

            if (!isPasswordValida(password)) {
                onResult(
                    false,
                    "La contraseña debe tener mínimo 6 caracteres, una mayúscula, un número y un símbolo"
                )
                return@launch
            }

            // --------------------------
            // ❌ VALIDACIONES DE ROOM (ELIMINADO)
            // Estas se quitaron por no corresponder a un registro online
            // --------------------------
            /*
            if (repository.obtenerUsuarioPorCorreo(correo) != null) {
                onResult(false, "El correo ya está registrado")
                return@launch
            }

            if (repository.obtenerUsuarioPorUsername(username) != null) {
                onResult(false, "El nombre de usuario ya existe")
                return@launch
            }
            */

            // --------------------------
            // ⭐ REGISTRO ONLINE (NUEVO)
            // --------------------------
            val ok = repository.registrarUsuario(
                nombre = nombre,
                correo = correo,
                username = username,
                password = password,
                comuna = comuna
            )

            if (ok) {
                onResult(true, null)
            } else {
                onResult(false, "Error al registrar usuario")
            }
        }
    }

    private fun isCorreoValido(correo: String): Boolean {
        return correo.contains("@") && android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }

    private fun isPasswordValida(password: String): Boolean {
        val regex = Regex("^(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{6,}$")
        return regex.matches(password)
    }
}
