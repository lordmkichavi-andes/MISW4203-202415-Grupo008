package co.edu.uniandes.vinilos.model.repository

import android.content.Context
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.models.Track
import co.edu.uniandes.vinilos.model.providers.AlbumProvider
import co.edu.uniandes.vinilos.network.CacheManager

class AlbumRepository(private val albumProvider: AlbumProvider) {

    fun addAbum(context: Context, album: Album, onSuccess: (Album) -> Unit, onError: (String) -> Unit) {
        albumProvider.addAbum(album, { registeredAlbum ->
            registeredAlbum.id?.let { CacheManager.getInstance(context).addAlbum(it, registeredAlbum) }
            onSuccess(registeredAlbum)
        }, { error ->
            onError(error)
        })
    }

    fun getAlbums(context: Context, onSuccess: (List<Album>) -> Unit, onError: (String) -> Unit) {
        val cacheAlbums = CacheManager.getInstance(context).getAlbums()
        if (cacheAlbums.isNullOrEmpty()) {
            albumProvider.getAlbums({ albumList ->
                CacheManager.getInstance(context).saveAlbums(albumList)
                onSuccess(albumList)
            }, { error ->
                onError(error)
            })
        } else {
            onSuccess(cacheAlbums)
        }
    }

    fun getAlbum(context: Context, albumID: Int, onSuccess: (Album) -> Unit, onError: (String) -> Unit) {
        val cacheAlbum = CacheManager.getInstance(context).getAlbum(albumID)
        if (cacheAlbum == null) {
            albumProvider.getAlbum(albumID, { album ->
                CacheManager.getInstance(context).addAlbum(albumID, album)
                onSuccess(album)
            }, { error ->
                onError(error)
            })
        } else {
            onSuccess(cacheAlbum)
        }
    }

    fun getTracksForAlbum(context: Context, albumID: Int, onSuccess: (List<Track>) -> Unit, onError: (String) -> Unit) {
        albumProvider.getTracksForAlbum(albumID, { trackList ->
            onSuccess(trackList)
        }, { error ->
            onError(error)
        })
    }

    fun addTrackToAlbum(context: Context, id: Int, track: Track, onSuccess: () -> Unit, onError: (String) -> Unit) {
        albumProvider.addTrackToAlbum(id, track, {
            onSuccess()
        }, { error ->
            onError(error)
        })
    }
}
