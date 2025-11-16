package cl.trashout.ev2_phonetruck.model.domain.data.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.trashout.ev2_phonetruck.model.domain.data.entities.FormRegistroEntity

@Dao
interface FormRegistroDao {

    // Registrar usuario
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertarUsuario(usuario: FormRegistroEntity)

    // Obtener usuario por correo
    @Query("SELECT * FROM form_registro WHERE correo = :correo LIMIT 1")
    suspend fun obtenerPorCorreo(correo: String): FormRegistroEntity?

    // Obtener usuario por username
    @Query("SELECT * FROM form_registro WHERE username = :username LIMIT 1")
    suspend fun obtenerPorUsername(username: String): FormRegistroEntity?

    // Login
    @Query("SELECT * FROM form_registro WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): FormRegistroEntity?

    // Actualizar password usando correo
    @Query("UPDATE form_registro SET password = :password WHERE correo = :correo")
    suspend fun actualizarPassword(correo: String, password: String)
}
