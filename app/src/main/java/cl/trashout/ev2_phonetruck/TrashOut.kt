package cl.trashout.ev2_phonetruck

import android.app.Application
import androidx.room.Room
import cl.trashout.ev2_phonetruck.domain.data.config.AppDatabase
import cl.trashout.ev2_phonetruck.domain.data.repository.FormRegistroRepository
import cl.trashout.ev2_phonetruck.domain.data.repository.UserRepository

class TrashOut : Application() {

    companion object {
        lateinit var database: AppDatabase
        lateinit var formRegistroRepository: FormRegistroRepository
        lateinit var userRepository: UserRepository
    }

    override fun onCreate() {
        super.onCreate()
        //*************Error no inicia si busca la basedeDatos***********
        // ✅ Esta es la forma correcta de crear una instancia Room
//        database = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java,
//            "app_database"
//        ).build()
//
//        // ✅ Inicializar tus repositorios
//        formRegistroRepository = FormRegistroRepository(database.formRegistroDao())
//        userRepository = UserRepository(database.formRegistroDao())
    }
}
