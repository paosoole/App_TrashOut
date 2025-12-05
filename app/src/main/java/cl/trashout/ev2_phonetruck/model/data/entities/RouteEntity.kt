package cl.trashout.ev2_phonetruck.model.data.entities
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routes")
data class RouteRoomEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val startedAt: Long,
    val endedAt: Long?,
    val pointsJson: String,
    val synced: Boolean = false
)