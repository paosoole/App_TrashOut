package cl.trashout.ev2_phonetruck.domain.model

import java.sql.Timestamp

data class UserLocationModel (
    val latitude : Double,
    val longitude : Double,
    val timestamp: Long = System.currentTimeMillis()

){
}