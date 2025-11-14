package cl.trashout.ev2_phonetruck.domain.data.repository


import cl.trashout.ev2_phonetruck.domain.data.DAO.FormRegistroDao
import cl.trashout.ev2_phonetruck.domain.data.entities.FormRegistroEntity

class UserRepository(private val dao: FormRegistroDao) {

    suspend fun registrarUsuario(
        nombre: String,
        correo: String,
        username: String,
        password: String,
        comuna: String
    ) {
        val user = FormRegistroEntity(
            nombre = nombre,
            correo = correo,
            username = username,
            password = password,
            comuna = comuna
        )
        dao.insertarUsuario(user)
    }

    suspend fun login(username: String, password: String) =
        dao.login(username, password)

    suspend fun obtenerUsuarioPorCorreo(correo: String) =
        dao.obtenerPorCorreo(correo)

    suspend fun obtenerUsuarioPorUsername(username: String) =
        dao.obtenerPorUsername(username)

    suspend fun actualizarPassword(correo: String, nuevaPass: String) =
        dao.actualizarPassword(correo, nuevaPass)
}
