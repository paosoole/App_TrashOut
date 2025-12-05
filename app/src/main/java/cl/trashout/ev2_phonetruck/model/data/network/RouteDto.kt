package cl.trashout.ev2_phonetruck.model.data.network

data class RouteDto(
    val userId: Long,
    val startedAt: Long,
    val endedAt: Long?,
    val points: List<PointDto>
)