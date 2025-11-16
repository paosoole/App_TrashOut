package cl.trashout.ev2_phonetruck.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ResetState(
    val emailDestino: String? = null,
    val error: String? = null,
    val loading: Boolean = false
)

class ResetViewModel : ViewModel() {

    // ⚠️ Simulación temporal (luego se conecta al repository)
    private val usuariosFake = mapOf(
        "paola" to "paola@correo.cl",
        "mario" to "mario@trashout.cl",
        "chofer1" to "chofer1@empresa.cl"
    )

    private val _estado = MutableStateFlow(ResetState())
    val estado = _estado.asStateFlow()

    fun validarUsuario(username: String) {
        viewModelScope.launch {
            _estado.value = ResetState(loading = true)

            delay(800) // simula request

            val correoEncontrado = usuariosFake[username]

            if (correoEncontrado == null) {
                _estado.value = ResetState(error = "El usuario '$username' no existe")
            } else {
                _estado.value = ResetState(emailDestino = correoEncontrado)
            }
        }
    }

    fun limpiar() {
        _estado.value = ResetState()
    }
}
