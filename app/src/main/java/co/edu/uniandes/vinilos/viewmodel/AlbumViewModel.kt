package co.edu.uniandes.vinilos.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.repository.AlbumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlbumViewModel(private val repository: AlbumRepository) : ViewModel() {
    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()
    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums = _albums.asStateFlow()
    private val _recentAddedAlbum = MutableStateFlow(false)
    val recentAddedAlbum = _recentAddedAlbum.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun addAlbum(album: Album, navController: NavController) {
        Log.d("AlbumViewModel", "Iniciando proceso de agregar álbum: ${album.name}")
        repository.addAbum(album,
            onSuccess = {
                Log.d("AlbumViewModel", "Álbum agregado exitosamente: ${album.name}")
                _message.value = "Álbum creado exitosamente"
                _recentAddedAlbum.value = true
                navController.navigate("get_albumes/Coleccionista/1")
            },
            onError = { error ->
                Log.e("AlbumViewModel", "Error al agregar álbum: $error")
                _message.value = error
            }
        )
    }

    fun loadAlbums() {
        Log.d("AlbumViewModel", "Iniciando carga de álbumes.")
        _isLoading.value = true
        viewModelScope.launch {
            repository.getAlbums(
                onSuccess = { albumList ->
                    Log.d("AlbumViewModel", "Álbumes cargados con éxito: ${albumList.size} álbumes obtenidos.")
                    _albums.value = albumList
                    _message.value = "Álbumes cargados exitosamente"
                    _isLoading.value = false
                },
                onError = { error ->
                    Log.e("AlbumViewModel", "Error al cargar álbumes: $error")
                    _message.value = "Error al cargar álbumes: $error"
                    _isLoading.value = false
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("AlbumViewModel", "Limpiando el ViewModel.")
    }
}
