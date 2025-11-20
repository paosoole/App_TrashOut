package cl.trashout.ev2_phonetruck.viewModel

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

            // 1. Validaciones de campos vacíos
            if (nombre.isBlank() || correo.isBlank() || username.isBlank() || password.isBlank()) {
                onResult(false, "Todos los campos son obligatorios")
                return@launch
            }

            // 2. Validar correo
            if (!isCorreoValido(correo)) {
                onResult(false, "Correo electrónico no válido")
                return@launch
            }

            // 3. Validar contraseña
            if (!isPasswordValida(password)) {
                onResult(
                    false,
                    "La contraseña debe tener mínimo 6 caracteres, una mayúscula, un número y un símbolo"
                )
                return@launch
            }
//            if(!ValidacionUtils. isValidPassword(password){
//                //error
//                }

            // 4. Validar duplicados
            if (repository.obtenerUsuarioPorCorreo(correo) != null) {
                onResult(false, "El correo ya está registrado")
                return@launch
            }

            if (repository.obtenerUsuarioPorUsername(username) != null) {
                onResult(false, "El nombre de usuario ya existe")
                return@launch
            }

            // 5. Registrar usuario
            try {
                repository.registrarUsuario(
                    nombre = nombre,
                    correo = correo,
                    username = username,
                    password = password,
                    comuna = comuna
                )
                onResult(true, null)

            } catch (e: Exception) {
                e.printStackTrace()
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
