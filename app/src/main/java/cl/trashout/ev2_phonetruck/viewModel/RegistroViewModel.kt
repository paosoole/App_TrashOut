package cl.trashout.ev2_phonetruck.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.trashout.ev2_phonetruck.model.domain.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegistroViewModel(
    private val repository: UserRepository
) : ViewModel() {

    /**
     * REGISTRO DE USUARIO
     */
    fun registrar(
        nombre: String,
        correo: String,
        username: String,
        password: String,
        comuna: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.registrarUsuario(
                    nombre = nombre,
                    correo = correo,
                    username = username,
                    password = password,
                    comuna = comuna
                )
                onResult(true, null)
            } catch (e: Exception){
                onResult(false, e.message)
            }
            // Validación básica
            if (nombre.isBlank() || correo.isBlank() || username.isBlank() || password.isBlank()) {
                onResult(false, "Todos los campos son obligatorios")
                return@launch
            }

            // Validar duplicados
            if (repository.obtenerUsuarioPorCorreo(correo) != null) {
                onResult(false, "El correo ya está registrado")
                return@launch
            }

            // Verificar si username ya existe
            if (repository.obtenerUsuarioPorUsername(username) != null) {
                onResult(false, "El nombre de usuario ya existe")
                return@launch
            }

            try {
                // Registrar usuario
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

    /**
     * LOGIN (usa username + password)
     */
    fun login(username: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val usuario = repository.login(username, password)
                if (usuario != null) {
                    onResult(true, null)
                } else {
                    onResult(false, "Usuario o contraseña incorrectos")
                }
            } catch (e: Exception) {
                onResult(false, "Error al iniciar sesión")
            }
        }
    }

    /**
     * RESTABLECER CONTRASEÑA (con correo)
     */
    fun resetPassword(correo: String, nuevaPass: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {

            if (correo.isBlank()) {
                onResult(false, "Debe ingresar su correo registrado")
                return@launch
            }

            val usuario = repository.obtenerUsuarioPorCorreo(correo)

            if (usuario == null) {
                onResult(false, "No existe un usuario con ese correo")
                return@launch
            }

            try {
                repository.actualizarPassword(correo, nuevaPass)
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, "No fue posible actualizar la contraseña")
            }
        }
    }
}