package co.edu.uniandes.vinilos.viewmodel

import androidx.lifecycle.ViewModel
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.repository.AlbumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AlbumViewModel(private val repository: AlbumRepository) : ViewModel() {

    fun addAlbum(album: Album, onSuccess: () -> Unit, onError: (String) -> Unit) {
        repository.addAbum(album,
            onSuccess = onSuccess,
            onError = onError
        )
    }
}