package co.edu.uniandes.vinilos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.uniandes.vinilos.model.repository.ArtistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistViewModel(private val artistRepository: ArtistRepository) : ViewModel() {

    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists: StateFlow<List<Artist>> = _artists

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private var currentPage = 0
    private val pageSize = 20
    private var isLastPage = false

    init {
        loadArtists()
    }

    fun loadArtists() {
        if (isLastPage || _isLoading.value) return

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newArtists = artistRepository.getArtistsPaginated(currentPage, pageSize)
                if (newArtists.size < pageSize) {
                    isLastPage = true
                }
                _artists.value = _artists.value + newArtists
                currentPage++
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar artistas: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshArtists() {
        _artists.value = emptyList()
        currentPage = 0
        isLastPage = false
        loadArtists()
    }
}
