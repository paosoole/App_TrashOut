package cl.trashout.ev2_phonetruck.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import cl.trashout.ev2_phonetruck.model.data.repository.UserRepository

data class ResetState(
    val emailDestino: String? = null,
    val error: String? = null,
    val loading: Boolean = false
)

class ResetViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _estado = MutableStateFlow(ResetState())
    val estado = _estado.asStateFlow()

    /** 🔍 Verifica si el usuario existe */
    fun validarUsuario(username: String) {
        viewModelScope.launch {
            _estado.value = ResetState(loading = true)

            val user = repository.obtenerUsuarioPorUsername(username)

            if (user == null) {
                _estado.value = ResetState(error = "El usuario '$username' no existe")
            } else {
                _estado.value = ResetState(emailDestino = user.correo)
            }
        }
    }

    /** ♻ Limpia el estado */
    fun limpiar() {
        _estado.value = ResetState()
    }


}
