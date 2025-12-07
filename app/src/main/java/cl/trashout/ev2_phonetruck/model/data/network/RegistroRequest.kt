package cl.trashout.ev2_phonetruck.model.data.network;

data class RegistroRequest(
    val nombre: String,
    val correo: String,
    val comuna: String,
    val username: String,
    val password: String
)