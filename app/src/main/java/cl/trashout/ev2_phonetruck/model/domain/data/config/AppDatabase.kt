package cl.trashout.ev2_phonetruck.model.domain.data.config


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.trashout.ev2_phonetruck.model.domain.data.DAO.FormRegistroDao
import cl.trashout.ev2_phonetruck.model.domain.data.entities.FormRegistroEntity
@Database(
    entities = [FormRegistroEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun formRegistroDao(): FormRegistroDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    }

