package co.edu.uniandes.vinilos.model.providers

import android.content.Context
import co.edu.uniandes.vinilos.model.models.Album
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.reflect.TypeToken

import com.google.gson.Gson


class AlbumProvider {
    val path = "https://vynils-miso-7f8e65a403b6.herokuapp.com/albums"

    fun getAlbums(context: Context, onSuccess: (List<Album>) -> Unit, onError: (String) -> Unit) {
        val requestQueue = Volley.newRequestQueue(context)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, path, null,
            { response ->
                try {
                    // Parsear el JSON array usando Gson
                    val listType = object : TypeToken<List<Album>>() {}.type
                    val albums: List<Album> = Gson().fromJson(response.toString(), listType)

                    // Imprimir en consola el listado de álbumes
                    albums.forEach { album ->
                        println("Álbum: ${album.name}, Artista: ${album.genre}, Año: ${album.releaseDate}")
                        album.tracks?.forEach { track ->
                            println("    Pista: ${track.name}, Duración: ${track.duration} segundos")
                        }
                    }

                    onSuccess(albums)
                } catch (e: Exception) {
                    onError("Error al parsear los datos")
                }
            },
            { error ->
                onError("Error en la solicitud: ${error.message}")
            }
        )

        requestQueue.add(jsonArrayRequest)
    }
}