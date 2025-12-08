package cl.trashout.ev2_phonetruck.model.data.network

data class LoginResponse(
    val message: String,
    val userId: Long?,
    val username: String?
)


