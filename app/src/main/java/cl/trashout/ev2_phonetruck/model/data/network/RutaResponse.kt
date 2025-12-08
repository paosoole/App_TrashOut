data class RutaResponse(
    val id: Long,
    val userId: Long,
    val startedAt: String,
    val endedAt: String,
    val pointsJson: String,
    val synced: Boolean
)