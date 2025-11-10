package cl.trashout.ev2_phonetruck.domain.data.entities


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "form_registro")
data class FormRegistroEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val correo: String,
    val password: String
)
