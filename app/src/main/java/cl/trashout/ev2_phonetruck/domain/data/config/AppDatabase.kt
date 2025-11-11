package cl.trashout.ev2_phonetruck.domain.data.config


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.trashout.ev2_phonetruck.domain.data.DAO.FormRegistroDao
import cl.trashout.ev2_phonetruck.domain.data.entities.FormRegistroEntity
@Database(
    entities = [FormRegistroEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun formRegistroDao(): FormRegistroDao
    companion object {
        @Volatile
        private var INSTANCE: cl.trashout.ev2_phonetruck.domain.data.config.AppDatabase? = null

        fun getDatabase(context: Context): cl.trashout.ev2_phonetruck.domain.data.config.AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    cl.trashout.ev2_phonetruck.domain.data.config.AppDatabase::class.java,
                    "app_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    }

