package cl.trashout.ev2_phonetruck.domain.data.repository


import cl.trashout.ev2_phonetruck.domain.data.DAO.FormRegistroDao
import cl.trashout.ev2_phonetruck.domain.data.entities.FormRegistroEntity

class UserRepository(private val dao: FormRegistroDao) {

    suspend fun insertarUsuario(nombre: String, correo: String, password: String) {
        dao.insertarUsuario(FormRegistroEntity(nombre = nombre, correo = correo, password = password))
    }

    suspend fun obtenerUsuarioPorCorreo(correo: String): FormRegistroEntity? {
        return dao.obtenerUsuarioPorCorreo(correo)
    }

    suspend fun login(correo: String, password: String): FormRegistroEntity? {
        return dao.login(correo, password)
    }

    suspend fun actualizarPassword(correo: String, nuevaPass: String) {
        dao.actualizarPassword(correo, nuevaPass)
    }
}
