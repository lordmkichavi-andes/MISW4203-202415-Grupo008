package co.edu.uniandes.vinilos.viewmodel

import android.content.Context
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
    private val _album = MutableStateFlow<Album>(Album(
        name = "",
        cover = "",
        releaseDate = "",
        description = "",
        genre = "",
        recordLabel = "",
    ))
    val album = _album.asStateFlow()

    private val _recentAddedAlbum = MutableStateFlow(false)
    val recentAddedAlbum = _recentAddedAlbum.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun addAlbum(context: Context, album: Album, navController: NavController) {
        Log.d("AlbumViewModel", "Iniciando proceso de agregar álbum: ${album.name}")
        repository.addAbum(
            context= context,
            album,
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

    fun loadAlbums(context: Context,) {
        Log.d("AlbumViewModel", "Iniciando carga de álbumes.")
        _isLoading.value = true
        viewModelScope.launch {
            repository.getAlbums(
                context= context,
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

    fun getAlbum(context: Context, albumID: Int) {
        Log.d("AlbumViewModel", "Iniciando carga de álbum con id: $albumID.")
        _isLoading.value = true
        viewModelScope.launch {
            repository.getAlbum(
                context= context,
                albumID,
                onSuccess = { album ->
                    Log.d("AlbumViewModel", "Álbum  ${album.name} cargado con éxito.")
                    _album.value = album
                    _message.value = "Álbum cargado exitosamente"
                    _isLoading.value = false
                },
                onError = { error ->
                    Log.e("AlbumViewModel", "Error al cargar álbum: $error")
                    _message.value = "Error al cargar álbum: $error"
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
