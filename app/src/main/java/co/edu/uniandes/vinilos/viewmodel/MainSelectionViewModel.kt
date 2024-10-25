package co.edu.uniandes.vinilos.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainSelectionViewModel : ViewModel() {
    private val _selectedProfile = MutableStateFlow<String?>(null)
    val selectedProfile: StateFlow<String?> = _selectedProfile

    fun selectProfile(profile: String) {
        _selectedProfile.value = profile
    }
}
