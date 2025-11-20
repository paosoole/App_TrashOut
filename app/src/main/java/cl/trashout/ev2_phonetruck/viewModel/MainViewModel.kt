package cl.trashout.ev2_phonetruck.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.trashout.ev2_phonetruck.model.UserLocationModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class
MainViewModel(application: Application) : AndroidViewModel(application) {

    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    // Ubicación actual del usuario
    private val _location = MutableStateFlow<UserLocationModel?>(null)
    val location = _location.asStateFlow()

    // Puntos del recorrido
    private val _routePoints = MutableStateFlow<List<LatLng>>(emptyList())
    val routePoints = _routePoints.asStateFlow()

    private var locationCallback: LocationCallback? = null

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        // ✅ Usar la nueva API de LocationRequest
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, // corregido el typo
            5000L // cada 5 segundos
        )
            .setMinUpdateDistanceMeters(5f) // actualizar si se mueve más de 5m
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { androidLocation -> // corregido el nombre
                    viewModelScope.launch {
                        val userLocation = UserLocationModel(
                            latitude = androidLocation.latitude,
                            longitude = androidLocation.longitude
                        )
                        _location.emit(userLocation)

                        // Agregar punto al recorrido
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

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback!!,
            null
        )
    }

//    fun stopLocationUpdates() {
//        locationCallback?.let {
//            fusedLocationProviderClient.removeLocationUpdates(it)
//        }
//    }

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