package cl.trashout.ev2_phonetruck.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.trashout.ev2_phonetruck.TrashOut
import kotlinx.coroutines.launch
import cl.trashout.ev2_phonetruck.model.data.entities.RouteRoomEntity
import cl.trashout.ev2_phonetruck.model.data.network.PointDto
import cl.trashout.ev2_phonetruck.model.data.network.RouteDto
import cl.trashout.ev2_phonetruck.model.data.repository.RouteRepository

class RutaViewModel(repoRuta1: RouteRepository) : ViewModel() {

    private val repoRuta = TrashOut.routeRepository

    fun sincronizarPendientes() {
        viewModelScope.launch {
            val pendientes = repoRuta.getRutasPendientes()

            for (ruta in pendientes) {
                val dto = ruta.toDto()     // Extensión abajo
                val ok = repoRuta.enviarRutaBackend(dto)

                if (ok) {
                    repoRuta.marcarComoSincronizada(ruta)
                }
            }
        }
    }
}

// Conversión Room → DTO
fun RouteRoomEntity.toDto(): RouteDto {
    val gson = com.google.gson.Gson()
    val points = gson.fromJson(pointsJson, Array<PointDto>::class.java).toList()

    return RouteDto(
        userId = userId,
        startedAt = startedAt,
        endedAt = endedAt ?: System.currentTimeMillis(),
        points = points
    )
}
