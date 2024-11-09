package co.edu.uniandes.vinilos.model.repository

import co.edu.uniandes.vinilos.model.providers.ArtistProviderAPI
import co.edu.uniandes.vinilos.viewmodel.Artist

class ArtistRepository(private val artistProvider: ArtistProviderAPI) {

    fun getArtists(onSuccess: (List<Artist>) -> Unit, onError: (String) -> Unit) {
        artistProvider.getArtists(onSuccess, onError)
    }

    suspend fun getArtistsPaginated(page: Int, pageSize: Int = 20): List<Artist> {
        return artistProvider.getArtistsPage(page, pageSize)
    }
}
