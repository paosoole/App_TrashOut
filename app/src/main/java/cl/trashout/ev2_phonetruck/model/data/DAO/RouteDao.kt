package cl.trashout.ev2_phonetruck.model.data.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cl.trashout.ev2_phonetruck.model.data.entities.RouteRoomEntity

@Dao
interface RouteDao {
    @Insert
    suspend fun insert(route: RouteRoomEntity)
    @Query("SELECT * FROM routes WHERE synced = 0") suspend fun unsynced(): List<RouteRoomEntity>
    @Update
    suspend fun update(route: RouteRoomEntity)
}