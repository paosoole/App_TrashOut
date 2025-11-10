package cl.trashout.ev2_phonetruck.domain.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.trashout.ev2_phonetruck.domain.data.repository.UserRepository

class RegistroViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistroViewModel::class.java)) {
            return RegistroViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
