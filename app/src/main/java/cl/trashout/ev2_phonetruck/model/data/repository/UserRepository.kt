package cl.trashout.ev2_phonetruck.model.data.repository

import cl.trashout.ev2_phonetruck.model.data.DAO.FormRegistroDao
import cl.trashout.ev2_phonetruck.model.data.network.*
import cl.trashout.ev2_phonetruck.model.data.entities.FormRegistroEntity
import cl.trashout.ev2_phonetruck.model.data.network.NetworkModule.api
import cl.trashout.ev2_phonetruck.model.data.network.ApiService

class UserRepository(
    private val dao: FormRegistroDao,
    private val api: ApiService = NetworkModule.api
) {

    // ---------------------------
    // REGISTRO EN BACKEND
    // ---------------------------
    suspend fun registrarUsuario(
        nombre: String,
        correo: String,
        username: String,
        password: String,
        comuna: String
    ): Boolean {

        val req = RegistroRequest(nombre, correo, comuna, username, password)

        return try {
            val resp = api.registrar(req)
            resp.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    // ---------------------------
    // LOGIN EN BACKEND
    // ---------------------------
    suspend fun login(username: String, password: String): LoginResponse? {
        return try {
            val resp = api.login(LoginRequest(username, password))
            if (resp.isSuccessful) resp.body() else null
        } catch (e: Exception) {
            null
        }
    }

    // ---------------------------
    // MÉTODOS LOCALES OPCIONALES (Room)
    // ---------------------------
    suspend fun obtenerUsuarioPorCorreo(correo: String) =
        dao.obtenerPorCorreo(correo)

    suspend fun obtenerUsuarioPorUsername(username: String) =
        dao.obtenerPorUsername(username)

    suspend fun actualizarPassword(correo: String, nuevaPass: String) =
        dao.actualizarPassword(correo, nuevaPass)


    // registrar ruta en bbdd
    suspend fun enviarRuta(routeDto: RouteDto): Boolean{
        return try {
            val response = NetworkModule.api.postRoute(routeDto)
            response.isSuccessful
        } catch (e: Exception){
            false
        }
    }
    // guarda local en room
    suspend fun guardarRutaLocal(
        userId: Long,
        startedAt: Long,
        endAt: Long?,
        pointsJson: String
    ){
    }
}


//class UserRepository(
//    private val dao: FormRegistroDao,
//    private val api: ApiService = NetworkModule.api
//) {
//
//    // Registro local (sigue usando Room)
//    suspend fun registrarUsuario(
//        nombre: String,
//        correo: String,
//        username: String,
//        password: String,
//        comuna: String
//    ) {
//        val user = FormRegistroEntity(
//            nombre = nombre,
//            correo = correo,
//            username = username,
//            password = password,
//            comuna = comuna
//        )
//        dao.insertarUsuario(user)
//    }
//    // Login: intenta backend, si no hay conexión intenta Room
//    suspend fun login(username: String, password: String) :
//            FormRegistroEntity?{
//        try{
//            val resp = api.login(LoginRequest(username,password))
//            if(resp.isSuccessful){
//                val body = resp.body()
//                return dao.obtenerPorUsername(username)?: FormRegistroEntity(
//                    nombre = body?.username?:username,
//                    correo = "",
//                    username = username,
//                    password = password,
//                    comuna = ""
//                )
//            }
//        } catch (e: Exception){
//            //fallo al guardar -> guarda en room
//        }
//        return dao.login(username, password)
//    }
//
//
//    suspend fun obtenerUsuarioPorCorreo(correo: String) =
//        dao.obtenerPorCorreo(correo)
//
//    suspend fun obtenerUsuarioPorUsername(username: String) =
//        dao.obtenerPorUsername(username)
//
//    suspend fun actualizarPassword(correo: String, nuevaPass: String) =
//        dao.actualizarPassword(correo, nuevaPass)
//
//
    // registrar ruta en bbdd
    suspend fun enviarRuta(routeDto: RouteDto): Boolean{
        return try {
            val response = api.postRoute(routeDto)
            response.isSuccessful
        } catch (e: Exception){
            false
        }
    }
    // guarda local en room
    suspend fun guardarRutaLocal(
        userId: Long,
        startedAt: Long,
        endAt: Long?,
        pointsJson: String
    ){
    }
//}
