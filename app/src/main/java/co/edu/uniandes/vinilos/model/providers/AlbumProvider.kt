package co.edu.uniandes.vinilos.model.providers

import co.edu.uniandes.vinilos.model.models.Album
import android.content.Context

interface AlbumProvider{
    fun addAbum(album: Album, onSuccess: () -> Unit, onError: (String) -> Unit)
    fun getAlbums(onSuccess: (List<Album>) -> Unit, onError: (String) -> Unit)
}
