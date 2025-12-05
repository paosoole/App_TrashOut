package cl.trashout.ev2_phonetruck.model


data class UserLocationModel (
    val latitude : Double,
    val longitude : Double,
    val timestamp: Long = System.currentTimeMillis()

)