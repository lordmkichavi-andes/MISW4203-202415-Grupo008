package co.edu.uniandes.vinilos.model.providers

import co.edu.uniandes.vinilos.model.models.Album

interface AlbumProvider{
    fun addAbum(album: Album, onSuccess: () -> Unit, onError: (String) -> Unit)
}