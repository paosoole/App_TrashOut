package cl.trashout.ev2_phonetruck.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.trashout.ev2_phonetruck.model.data.repository.UserRepository
import cl.trashout.ev2_phonetruck.model.LoginUIState
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
            val resp = repository.login(s.username, s.password)

            if (resp != null && resp.message.contains("LOGIN_OK", ignoreCase = true)) {
                _estado.update { it.copy(error = null) }
                onSuccess()
            } else {
                _estado.update { it.copy(error = resp?.message ?: "Usuario o contraseña incorrectos") }
            }
        }
    }
    fun setError(msg: String) {
        _estado.value = _estado.value.copy(error = msg)
    }

    }