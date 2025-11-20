package cl.trashout.ev2_phonetruck

import android.app.Application
import android.util.Log
import androidx.room.Room
import cl.trashout.ev2_phonetruck.model.data.config.AppDatabase
import cl.trashout.ev2_phonetruck.model.data.repository.UserRepository
import com.google.android.gms.maps.MapsInitializer

class TrashOut : Application() {

    companion object {
        lateinit var database: AppDatabase
        lateinit var userRepository: UserRepository
    }

    override fun onCreate()
    {
        super.onCreate()
        // ✅ Esta es la forma correcta de crear una instancia Room
        database = Room.databaseBuilder(
            applicationContext,
               AppDatabase::class.java,
               "TrashOut_DB"
           ).build()
        // ✅ Inicializar Google Maps
        try {
            MapsInitializer.initialize(applicationContext)
        } catch (e: Exception) {
            Log.e("TrashOut", "Error initializing Google Maps", e)
        }
        // Inicializar  repositorio
        userRepository = UserRepository(database.formRegistroDao())


    }
}
