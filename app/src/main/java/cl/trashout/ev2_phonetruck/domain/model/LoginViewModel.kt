package cl.trashout.ev2_phonetruck.domain.model

import cl.trashout.ev2_phonetruck.domain.data.repository.UserRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    var loginSuccess: Boolean = false
    var loginError: String? = null

    fun iniciarSesion(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            val user = repository.login(email, password)
            if (user != null) {
                onResult(true, null)
            } else {
                onResult(false, "Usuario o contraseña incorrectos")
            }
        }
    }
}