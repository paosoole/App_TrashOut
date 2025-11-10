package cl.trashout.ev2_phonetruck.domain.data.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.trashout.ev2_phonetruck.domain.data.entities.FormRegistroEntity

@Dao
interface FormRegistroDao {

    // Inserta o reemplaza un usuario
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: FormRegistroEntity): Long

    // Obtiene un usuario por su correo
    @Query("SELECT * FROM form_registro WHERE correo = :correo LIMIT 1")
    suspend fun obtenerUsuarioPorCorreo(correo: String): FormRegistroEntity?

    // Verifica login (correo y contraseña)
    @Query("SELECT * FROM form_registro WHERE correo = :correo AND password = :password LIMIT 1")
    suspend fun login(correo: String, password: String): FormRegistroEntity?

    // Actualiza contraseña
    @Query("UPDATE form_registro SET password = :nuevaPass WHERE correo = :correo")
    suspend fun actualizarPassword(correo: String, nuevaPass: String)

    // Elimina todos los registros
    @Query("DELETE FROM form_registro")
    suspend fun deleteAll()
}
