package co.edu.uniandes.vinilos.network

import android.content.Context
import android.util.SparseArray
import co.edu.uniandes.vinilos.model.models.Album


class CacheManager(context: Context) {
    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }
    private var albums:  HashMap<Int, Album> = hashMapOf()
    fun addAlbum(albumId: Int, album: Album){
        if (albums[albumId] == null){
           albums[albumId] = album
        }
    }

    fun saveAlbums(albums: List<Album>) {
        for(album in albums){
            album.id?.let { addAlbum(it, album) }
        }
    }


    fun getAlbums() : List<Album> {
        return ArrayList(albums.values)
    }

    fun getAlbum(albumID: Int): Album? {
        if (albums[albumID] != null){
           return  albums[albumID]
        }
        return null
    }


}

