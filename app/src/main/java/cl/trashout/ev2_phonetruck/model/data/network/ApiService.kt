package cl.trashout.ev2_phonetruck.model.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("api/usuarios/registro")
    suspend fun registrar(@Body request: RegistroRequest): Response<MensajeResponse>

    @POST("api/usuarios/login")
    suspend fun login(@Body request: LoginRequest): Response<MensajeResponse>

    @POST("api/routes") suspend fun postRoute(@Body route: RouteDto):
            Response<Map<String, Any>>
    @GET("api/routes/user/{userId}")
    suspend fun getRoutesForUser(@Path("userId") userId: Long):
            Response<List<Map<String, Any>>>
}
