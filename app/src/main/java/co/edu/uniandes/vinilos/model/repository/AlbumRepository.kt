package co.edu.uniandes.vinilos.model.repository

import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.providers.AlbumProvider
import android.content.Context

class AlbumRepository(val albumProvider: AlbumProvider) {

    fun addAbum(album: Album, onSuccess: () -> Unit, onError: (String) -> Unit) {
        albumProvider.addAbum(album, onSuccess, onError)
    }

    fun getAlbums(onSuccess: (List<Album>) -> Unit, onError: (String) -> Unit) {
        albumProvider.getAlbums(onSuccess, onError)
    }
}
