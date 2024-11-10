package co.edu.uniandes.vinilos.model.repository

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.providers.AlbumProvider
import co.edu.uniandes.vinilos.network.CacheManager

class AlbumRepository(val albumProvider: AlbumProvider) {

    fun addAbum(context: Context, album: Album, onSuccess: (Album) -> Unit, onError: (String) -> Unit) {
        Log.d("AlbumRepository", "Enviando solicitud para agregar álbum: ${album.name}")
        albumProvider.addAbum(album, {registeredAlbum ->
            Log.d("AlbumRepository", "Álbum agregado exitosamente.")
            registeredAlbum.id?.let { CacheManager.getInstance(context).addAlbum(it, registeredAlbum) }
            onSuccess(registeredAlbum)
        }, { error ->
            Log.e("AlbumRepository", "Error al agregar álbum: $error")
            onError(error)
        })
    }

    fun getAlbums( context: Context, onSuccess: (List<Album>) -> Unit, onError: (String) -> Unit) {
        val cacheAlbums = CacheManager.getInstance(context).getAlbums()
        if (cacheAlbums.isNullOrEmpty()) {
            Log.d("AlbumRepository", "Iniciando solicitud para obtener álbumes.")
            albumProvider.getAlbums({ albumList ->
                Log.d("AlbumRepository", "Álbumes obtenidos exitosamente: ${albumList.size} álbumes.")
                CacheManager.getInstance(context).saveAlbums(albumList)
                onSuccess(albumList)
            }, { error ->
                Log.e("AlbumRepository", "Error al obtener álbumes: $error")
                onError(error)
            })
        } else {
            Log.d("AlbumRepository", "Obteniendo albums desde memoria caché.")
            onSuccess(cacheAlbums)
        }


    }

    fun getAlbum( context: Context, albumID: Int,  onSuccess: (Album) -> Unit, onError: (String) -> Unit) {
        val cacheAlbum = CacheManager.getInstance(context).getAlbum(albumID)
        if (cacheAlbum == null) {
            Log.d("AlbumRepository", "Iniciando solicitud para obtener álbumes.")
            albumProvider.getAlbum(
                albumID,
                { album ->
                Log.d("AlbumRepository", "Álbum ${album.name} obtenido exitosament.")
                onSuccess(album)
            }, { error ->
                Log.e("AlbumRepository", "Error al obtener álbum: $error")
                onError(error)
            })
        } else {
            Log.d("AlbumRepository", "Obteniendo album desde memoria caché.")
            onSuccess(cacheAlbum)
        }


    }
}
