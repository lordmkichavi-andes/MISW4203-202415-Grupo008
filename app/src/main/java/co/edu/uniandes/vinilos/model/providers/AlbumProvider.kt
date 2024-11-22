package co.edu.uniandes.vinilos.model.providers

import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.models.Track

interface AlbumProvider {
    fun addAbum(album: Album, onSuccess: (Album) -> Unit, onError: (String) -> Unit)
    fun getAlbums(onSuccess: (List<Album>) -> Unit, onError: (String) -> Unit)
    fun getAlbum(albumID: Int, onSuccess: (Album) -> Unit, onError: (String) -> Unit)
    fun getTracksForAlbum(albumID: Int, onSuccess: (List<Track>) -> Unit, onError: (String) -> Unit)
    fun addTrackToAlbum(albumID: Int, track: Track, onSuccess: () -> Unit, onError: (String) -> Unit)
}
