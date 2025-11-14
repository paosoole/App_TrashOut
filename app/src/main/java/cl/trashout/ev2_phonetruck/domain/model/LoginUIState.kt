package cl.trashout.ev2_phonetruck.domain.model

data class LoginUIState (
    val username: String = "",
    val password: String = "",
    val error: String? = null,
    val loading: Boolean = false
)