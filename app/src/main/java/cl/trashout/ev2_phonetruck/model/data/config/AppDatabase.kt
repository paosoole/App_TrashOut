package cl.trashout.ev2_phonetruck.model.data.config

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.trashout.ev2_phonetruck.model.data.DAO.FormRegistroDao
import cl.trashout.ev2_phonetruck.model.data.DAO.RouteDao
import cl.trashout.ev2_phonetruck.model.data.entities.FormRegistroEntity
import cl.trashout.ev2_phonetruck.model.data.entities.RouteRoomEntity

@Database(
    entities = [FormRegistroEntity::class,
                RouteRoomEntity::class],
    version = 1,
    exportSchema = false,

)
abstract class AppDatabase : RoomDatabase() {

    abstract fun formRegistroDao(): FormRegistroDao

    abstract fun routeDao(): RouteDao
    }

