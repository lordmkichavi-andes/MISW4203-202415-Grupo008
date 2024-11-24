package co.edu.uniandes.vinilos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.uniandes.vinilos.model.models.Collector
import co.edu.uniandes.vinilos.model.repository.CollectorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollectorViewModel(private val collectorRepository: CollectorRepository): ViewModel() {

    private val _collectors = MutableStateFlow<List<Collector>>(emptyList())
    val collectors: StateFlow<List<Collector>> = _collectors

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private var currentPage = 0
    private val pageSize = 20
    private var isLastPage = false

    init {
        loadCollectors()
    }

    fun loadCollectors() {
        if (isLastPage || _isLoading.value) return

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newCollectors = collectorRepository.getCollectors(currentPage, pageSize)
                if (newCollectors.size < pageSize) {
                    isLastPage = true
                }
                _collectors.value = _collectors.value + newCollectors
                currentPage++
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar artistas: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}