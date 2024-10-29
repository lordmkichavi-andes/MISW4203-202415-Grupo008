package co.edu.uniandes.vinilos.viewmodel

import androidx.lifecycle.ViewModel
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.repository.AlbumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AlbumViewModel(private val repository: AlbumRepository) : ViewModel() {
    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun addAlbum(album: Album) {
        repository.addAbum(album,
            onSuccess = {
                _message.value = "Álbum creado exitosamente"
            },
            onError = { error ->
                _message.value = error
            }
        )
    }
}