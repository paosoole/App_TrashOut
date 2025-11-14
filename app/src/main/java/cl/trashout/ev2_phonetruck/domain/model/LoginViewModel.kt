package cl.trashout.ev2_phonetruck.domain.model

import cl.trashout.ev2_phonetruck.domain.data.repository.UserRepository
import cl.trashout.ev2_phonetruck.domain.model.LoginUIState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {

    // Estado interno mutable
    private val _estado = MutableStateFlow(LoginUIState())
    val estado: StateFlow<LoginUIState> = _estado.asStateFlow()

    // Actualizar campos
    fun onUsernameChange(v: String) =
        _estado.update { it.copy(username = v, error = null) }

    fun onPasswordChange(v: String) =
        _estado.update { it.copy(password = v, error = null) }

    fun iniciarSesion(onSuccess: () -> Unit) {
        val s = _estado.value

        viewModelScope.launch {
            val result = repository.login(s.username, s.password)

            if (result != null) {
                // Login OK → limpiar error
                _estado.update { it.copy(error = null) }
                onSuccess()
            } else {
                // Error al autenticar
                _estado.update { it.copy(error = "Usuario o contraseña incorrectos") }
            }
        }
    }
}
