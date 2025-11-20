package cl.trashout.ev2_phonetruck.model.data.config

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.trashout.ev2_phonetruck.model.data.DAO.FormRegistroDao
import cl.trashout.ev2_phonetruck.model.data.entities.FormRegistroEntity
@Database(
    entities = [FormRegistroEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun formRegistroDao(): FormRegistroDao


    }

