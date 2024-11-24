package co.edu.uniandes.vinilos.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.models.Track
import co.edu.uniandes.vinilos.model.repository.AlbumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlbumViewModel(private val repository: AlbumRepository) : ViewModel() {

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums = _albums.asStateFlow()

    private val _album = MutableStateFlow<Album>(
        Album(
            name = "",
            cover = "",
            releaseDate = "",
            description = "",
            genre = "",
            recordLabel = ""
        )
    )
    val album = _album.asStateFlow()

    private val _recentAddedAlbum = MutableStateFlow(false)
    val recentAddedAlbum = _recentAddedAlbum.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks = _tracks.asStateFlow()

    fun addAlbum(context: Context, album: Album, navController: NavController) {
        repository.addAbum(
            context = context,
            album,
            onSuccess = {
                _message.value = "Álbum creado exitosamente"
                _recentAddedAlbum.value = true
                navController.navigate("get_albumes/Coleccionista/1")
            },
            onError = { error ->
                _message.value = error
            }
        )
    }

    fun loadAlbums(context: Context) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getAlbums(
                context = context,
                onSuccess = { albumList ->
                    _albums.value = albumList
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

    fun getAlbum(context: Context, albumID: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getAlbum(
                context = context,
                albumID = albumID,
                onSuccess = { album ->
                    _album.value = album
                    _message.value = "Álbum cargado exitosamente"
                    _isLoading.value = false
                },
                onError = { error ->
                    _message.value = "Error al cargar álbum: $error"
                    _isLoading.value = false
                }
            )
        }
    }

    fun loadTracks(context: Context, albumID: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getTracksForAlbum(
                context = context,
                albumID = albumID,
                onSuccess = { trackList ->
                    _tracks.value = trackList
                    _isLoading.value = false
                },
                onError = { error ->
                    _message.value = "Error al cargar tracks: $error"
                    _isLoading.value = false
                }
            )
        }
    }

    fun addTrackToAlbum(context: Context, albumID: Int, trackName: String, trackDuration: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (trackName.isBlank()) {
                    _message.value = "El nombre del track no puede estar vacío."
                    return@launch
                }
                if (!trackDuration.matches(Regex("^\\d{2}:\\d{2}\$"))) {
                    _message.value = "La duración debe estar en el formato HH:MM."
                    return@launch
                }

                repository.addTrackToAlbum(
                    context = context,
                    id = albumID,
                    track = Track(name = trackName, duration = trackDuration),
                    onSuccess = {
                        loadTracks(context, albumID)
                        _message.value = "Track agregado exitosamente."
                    },
                    onError = { error ->
                        _message.value = "Error al agregar el track: $error"
                    }
                )
            } catch (e: Exception) {
                _message.value = "Ocurrió un error inesperado. Intenta nuevamente."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateMessage(newMessage: String) {
        _message.value = newMessage
        viewModelScope.launch {
            kotlinx.coroutines.delay(3000)
            _message.value = ""
        }
    }

    fun loadAlbumsByIds(context: Context, albumIds: List<Int>) {
        _isLoading.value = true
        viewModelScope.launch {
            val albums = mutableListOf<Album>()
            for (id in albumIds) {
                try {
                    repository.getAlbum(
                        context = context,
                        albumID = id,
                        onSuccess = { album ->
                            albums.add(album)
                        },
                        onError = { error ->
                            _message.value = "Error al cargar el álbum con id $id: $error"
                        }
                    )
                } catch (e: Exception) {
                    _message.value = "Error inesperado al cargar el álbum con id $id."
                }
            }
            _albums.value = albums
            _isLoading.value = false
        }
    }

}
