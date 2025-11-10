package cl.trashout.ev2_phonetruck.domain.model

data class UsuarioModel (
    val username: String = "",
    val password: String = "",
    val error: String? = null,
    val loading: Boolean = false
){
}