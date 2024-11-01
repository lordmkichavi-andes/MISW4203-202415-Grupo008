package co.edu.uniandes.vinilos.viewmodel

import androidx.lifecycle.ViewModel
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.repository.AlbumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class AlbumViewModel(private val repository: AlbumRepository) : ViewModel() {
    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()
    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums = _albums.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Definir un Job y un CoroutineScope personalizado
    private val viewModelJob = Job()
    private val customScope = CoroutineScope(Dispatchers.Main + viewModelJob)

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

    fun loadAlbums() {
        _isLoading.value = true
        viewModelScope.launch { // Cambia customScope a viewModelScope
            repository.getAlbums(
                onSuccess = { albumList ->
                    println("loadAlbums - Data loaded: $albumList") // Debug
                    _albums.value = albumList // Actualiza el flujo de álbumes
                    _message.value = "Álbumes cargados exitosamente"
                    _isLoading.value = false
                },
                onError = { error ->

                    _message.value = "Error al cargar álbumes: $error"
                    _isLoading.value = false
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun addAlbum(album: Album, onSuccess: () -> Unit, onError: (String) -> Unit) {
        repository.addAbum(album,
            onSuccess = onSuccess,
            onError = onError
        )
    }
}
