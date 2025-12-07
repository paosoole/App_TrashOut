package cl.trashout.ev2_phonetruck.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.trashout.ev2_phonetruck.model.data.repository.RouteRepository


class RutaViewModelFactory(
    private val repoRuta: RouteRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RutaViewModel::class.java)) {
            return RutaViewModel(repoRuta) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}