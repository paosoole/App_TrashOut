package cl.trashout.ev2_phonetruck.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.trashout.ev2_phonetruck.TrashOut
import cl.trashout.ev2_phonetruck.model.UserLocationModel
import cl.trashout.ev2_phonetruck.model.data.network.PointDto
import cl.trashout.ev2_phonetruck.model.data.network.RouteDto
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    //Repositorio
    private val repository = TrashOut.userRepository

    private val routeRepository = TrashOut.routeRepository

    // Ubicación actual del usuario
    private val _location = MutableStateFlow<UserLocationModel?>(null)
    val location = _location.asStateFlow()

    // Puntos del recorrido
    private val _routePoints = MutableStateFlow<List<LatLng>>(emptyList())
    val routePoints = _routePoints.asStateFlow()
    // Estado de grabación (true = estamos registrando puntos)
    private val _isRecording = MutableStateFlow(false)
    val isRecording = _isRecording.asStateFlow()

    //controlador para ubicacion
    private var locationCallback: LocationCallback? = null

    // Tiempo y estado de la ruta actual
    private var routeStartTime: Long = 0L
    private var currentUserId: Long = 0L //Setear desde Login

    //Gson para union de ountos para guardar localmente
    private val gson = Gson()
    /** Permite que otra capa (ej: LoginViewModel / pantalla) asigne el userId actual */
    fun setUserId(id: Long) {
        currentUserId = id
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        // Si ya existe un callback, no duplicar
        if (locationCallback != null) return

        //  Usar la nueva API de LocationRequest
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, // corregido el typo
            5000L // cada 5 segundos
        )
            .setMinUpdateDistanceMeters(5f) // actualizar si se mueve más de 5m
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { androidLocation ->
                    viewModelScope.launch {
                        val userLocation = UserLocationModel(
                            latitude = androidLocation.latitude,
                            longitude = androidLocation.longitude,
                            timestamp = System.currentTimeMillis()
                        )
                        _location.emit(userLocation)

                        // Solo agregar puntos si estamos grabando
                        if (_isRecording.value) {
                            val updatedRoute = _routePoints.value.toMutableList()
                            updatedRoute.add(
                                LatLng(
                                    userLocation.latitude,
                                    userLocation.longitude
                                )
                            )
                            _routePoints.emit(updatedRoute)
                        }
                    }
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback!!,
            null
        )
    }

    fun stopLocationUpdates() {
        locationCallback?.let {
            fusedLocationProviderClient.removeLocationUpdates(it)
            locationCallback = null
        }
    }

    /** Inicia la grabación de la ruta */
    fun startRecording(){
        // marcar estado y tiempo de inicio
        _isRecording.value = true
        routeStartTime = System.currentTimeMillis()
        // limpiar puntos
        _routePoints.value = emptyList()
        // aseguramos actualizacion de la ubicacion
        startLocationUpdates()
    }

    /** Detiene la grabación y sincroniza la ruta (intenta enviar; si falla guarda local) */
    fun stopRecording(){
        _isRecording.value = false
        val endedAt = System.currentTimeMillis()

        //copiar puntos
        val collectedPoints = _routePoints.value.toList()

        //detener actualizacion cuando no se necesita
        startLocationUpdates()

        //enviar y guardar
        viewModelScope.launch {
        // Convertir a DTOs con timestamps (si no tienes timestamps por punto, se usa endedAt o System)        }
            val pointDtos = collectedPoints.map{latLng ->
                PointDto(
                    latitude = latLng.latitude,
                    longitude = latLng.longitude,
                    timestamp = System.currentTimeMillis()
                )

            }
            val routeDto = RouteDto(
                userId = currentUserId,
                startedAt = routeStartTime,
                endedAt = endedAt,
                points = pointDtos
            )
            val sent = try {
                routeRepository.enviarRutaBackend(routeDto)
            }catch (e: Exception){
                false
            }
            if (!sent){
                val pointsJson = gson.toJson(pointDtos)
                try {
                    routeRepository.guardarRutaLocal(
                        userId = currentUserId,
                        startedAt = routeStartTime,
                        endedAt = endedAt,
                        pointsJson = pointsJson)
                }catch (e: Exception){

                }
            }
        }
    }

//    @SuppressLint("MissingPermission")
//    fun getLastKnownLocation() {
//        fusedLocationProviderClient.lastLocation
//            .addOnSuccessListener { androidLocation ->
//                androidLocation?.let { loc ->
//                    viewModelScope.launch {
//                        val userLocation = UserLocationModel(
//                            latitude = loc.latitude,
//                            longitude = loc.longitude
//                        )
//                        _location.emit(userLocation)
//                    }
//                }
//            }
//    }
}