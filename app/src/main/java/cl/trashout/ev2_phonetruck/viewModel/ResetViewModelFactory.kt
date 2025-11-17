package cl.trashout.ev2_phonetruck.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.trashout.ev2_phonetruck.domain.data.repository.UserRepository

class ResetViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
