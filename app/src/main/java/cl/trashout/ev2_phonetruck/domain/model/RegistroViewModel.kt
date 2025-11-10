package cl.trashout.ev2_phonetruck.domain.model



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.trashout.ev2_phonetruck.domain.data.repository.UserRepository
import kotlinx.coroutines.launch
class RegistroViewModel(
    private val repository: UserRepository
) : ViewModel() {

    /**
     * Registrar un nuevo usuario
     */
    fun registrar(nombre: String, correo: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                if (nombre.isBlank() || correo.isBlank() || password.isBlank()) {
                    onResult(false, "Todos los campos son obligatorios")
                    return@launch
                }

                val existe = repository.obtenerUsuarioPorCorreo(correo)
                if (existe != null) {
                    onResult(false, "El correo ya está registrado")
                    return@launch
                }

                repository.insertarUsuario(nombre, correo, password)
                onResult(true, null)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(false, e.localizedMessage)
            }
        }
    }

    /**
     * Validar inicio de sesión
     */
    fun login(correo: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val usuario = repository.login(correo, password)
                if (usuario != null) {
                    onResult(true, null)
                } else {
                    onResult(false, "Credenciales inválidas")
                }
            } catch (e: Exception) {
                onResult(false, e.localizedMessage)
            }
        }
    }

    /**
     * Restablecer contraseña
     */
    fun resetPassword(correo: String, nuevaPass: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val usuario = repository.obtenerUsuarioPorCorreo(correo)
                if (usuario == null) {
                    onResult(false, "Usuario no encontrado")
                    return@launch
                }

                repository.actualizarPassword(correo, nuevaPass)
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.localizedMessage)
            }
        }
    }
}
