package cl.trashout.ev2_phonetruck.model

data class LoginUIState (
    val username: String = "",
    val password: String = "",
    val error: String? = null,
    val loading: Boolean = false,
    val userId: Long = 0
)