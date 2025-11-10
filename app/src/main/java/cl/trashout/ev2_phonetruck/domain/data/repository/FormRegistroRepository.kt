package cl.trashout.ev2_phonetruck.domain.data.repository

import cl.trashout.ev2_phonetruck.domain.data.DAO.FormRegistroDao
import cl.trashout.ev2_phonetruck.domain.data.entities.FormRegistroEntity

class FormRegistroRepository(private val dao: FormRegistroDao) {

    // Obtiene usuario por correo
    suspend fun obtenerUsuarioPorCorreo(correo: String): FormRegistroEntity? {
        return dao.obtenerUsuarioPorCorreo(correo)
    }

    // Guarda un formulario (usuario)
    suspend fun guardarFormulario(usuario: FormRegistroEntity): Long {
        return dao.insertarUsuario(usuario)
    }

    // Login
    suspend fun login(correo: String, password: String): FormRegistroEntity? {
        return dao.login(correo, password)
    }

    // Actualiza contraseña
    suspend fun actualizarPassword(correo: String, nuevaPass: String) {
        dao.actualizarPassword(correo, nuevaPass)
    }

    // Limpia la tabla completa
    suspend fun limpiar() {
        dao.deleteAll()
    }
}
