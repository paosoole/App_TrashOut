package cl.trashout.ev2_phonetruck.model.data.network

// ApiService.kt
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.Response

interface ApiService {
    @POST("api/users/login")
    suspend fun login(@Body req: LoginRequest): Response<LoginResponse>

    @POST("api/users/register")
    suspend fun register(@Body user: Map<String, String>): Response<Void>

    @POST("api/routes")
    suspend fun postRoute(@Body route: RouteDto): Response<Map<String, Any>>

    @GET("api/routes/user/{userId}")
    suspend fun getRoutesForUser(@Path("userId") userId: Long): Response<List<Map<String, Any>>>
}
