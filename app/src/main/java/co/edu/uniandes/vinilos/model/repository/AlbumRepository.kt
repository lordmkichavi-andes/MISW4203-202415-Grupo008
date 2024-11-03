package co.edu.uniandes.vinilos.model.repository

import android.util.Log
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.providers.AlbumProvider

class AlbumRepository(val albumProvider: AlbumProvider) {

    fun addAbum(album: Album, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d("AlbumRepository", "Enviando solicitud para agregar álbum: ${album.name}")
        albumProvider.addAbum(album, {
            Log.d("AlbumRepository", "Álbum agregado exitosamente.")
            onSuccess()
        }, { error ->
            Log.e("AlbumRepository", "Error al agregar álbum: $error")
            onError(error)
        })
    }

    fun getAlbums(onSuccess: (List<Album>) -> Unit, onError: (String) -> Unit) {
        Log.d("AlbumRepository", "Iniciando solicitud para obtener álbumes.")
        albumProvider.getAlbums({ albumList ->
            Log.d("AlbumRepository", "Álbumes obtenidos exitosamente: ${albumList.size} álbumes.")
            onSuccess(albumList)
        }, { error ->
            Log.e("AlbumRepository", "Error al obtener álbumes: $error")
            onError(error)
        })
    }
}
