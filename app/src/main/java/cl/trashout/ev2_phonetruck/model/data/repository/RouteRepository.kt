package cl.trashout.ev2_phonetruck.model.data.repository


import cl.trashout.ev2_phonetruck.model.data.DAO.RouteDao
import cl.trashout.ev2_phonetruck.model.data.entities.RouteRoomEntity
import cl.trashout.ev2_phonetruck.model.data.network.NetworkModule
import cl.trashout.ev2_phonetruck.model.data.network.RouteDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RouteRepository(
    private val routeDao: RouteDao
) {

    // =============================
    // 1. Guardar ruta local
    // =============================
    suspend fun guardarRutaLocal(
        userId: Long,
        startedAt: Long,
        endedAt: Long?,
        pointsJson: String
    ) {
        val entity = RouteRoomEntity(
            userId = userId,
            startedAt = startedAt,
            endedAt = endedAt,
            pointsJson = pointsJson,
            synced = false
        )
        routeDao.insert(entity)
    }

    // =============================
    // 2. Enviar ruta al backend
    // =============================
    suspend fun enviarRutaBackend(routeDto: RouteDto): Boolean {
        return try {
            val resp = NetworkModule.api.postRoute(routeDto)
            resp.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    // =============================
    // 3. Obtener rutas pendientes
    // =============================
    suspend fun getRutasPendientes(): List<RouteRoomEntity> =
        routeDao.unsynced()

    // =============================
    // 4. Marcar ruta como sincronizada
    // =============================
    suspend fun marcarComoSincronizada(entity: RouteRoomEntity) {
        routeDao.update(entity.copy(synced = true))
    }
}
