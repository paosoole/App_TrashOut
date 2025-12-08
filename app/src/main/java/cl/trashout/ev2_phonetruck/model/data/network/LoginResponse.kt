package cl.trashout.ev2_phonetruck.model.data.network

data class LoginResponse(
    val userId: Long,
    val username: String,
    val token: String? = null

)


