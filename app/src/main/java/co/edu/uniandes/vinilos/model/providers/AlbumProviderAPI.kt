package co.edu.uniandes.vinilos.model.providers

import android.content.Context
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.models.Track
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class AlbumProviderAPI(private val context: Context) : AlbumProvider {
    private val baseUrl = "https://vynils-miso-7f8e65a403b6.herokuapp.com/albums"

    override fun getAlbums(onSuccess: (List<Album>) -> Unit, onError: (String) -> Unit) {
        val requestQueue = Volley.newRequestQueue(context)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, baseUrl, null,
            { response ->
                try {
                    val listType = object : TypeToken<List<Album>>() {}.type
                    val albums: List<Album> = Gson().fromJson(response.toString(), listType)
                    onSuccess(albums)
                } catch (e: Exception) {
                    onError("Error al parsear los datos: ${e.localizedMessage}")
                }
            },
            { error ->
                onError("Error en la solicitud: ${error.message}")
            }
        )
        requestQueue.add(jsonArrayRequest)
    }

    override fun addAbum(album: Album, onSuccess: (Album) -> Unit, onError: (String) -> Unit) {
        val requestQueue = Volley.newRequestQueue(context)
        val albumJson = JSONObject(Gson().toJson(album))

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, baseUrl, albumJson,
            { response ->
                val albumType = object : TypeToken<Album>() {}.type
                val newAlbum: Album = Gson().fromJson(response.toString(), albumType)
                onSuccess(newAlbum)
            },
            { error ->
                onError("Error en la solicitud: ${error.message}")
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

    override fun getAlbum(albumID: Int, onSuccess: (Album) -> Unit, onError: (String) -> Unit) {
        val requestQueue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, "$baseUrl/$albumID", null,
            { response ->
                try {
                    val albumType = object : TypeToken<Album>() {}.type
                    val album: Album = Gson().fromJson(response.toString(), albumType)
                    onSuccess(album)
                } catch (e: Exception) {
                    onError("Error al parsear los datos: ${e.localizedMessage}")
                }
            },
            { error ->
                onError("Error en la solicitud: ${error.message}")
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

    override fun getTracksForAlbum(albumID: Int, onSuccess: (List<Track>) -> Unit, onError: (String) -> Unit) {
        val requestQueue = Volley.newRequestQueue(context)
        val url = "$baseUrl/$albumID/tracks"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val listType = object : TypeToken<List<Track>>() {}.type
                    val tracks: List<Track> = Gson().fromJson(response.toString(), listType)
                    onSuccess(tracks)
                } catch (e: Exception) {
                    onError("Error al parsear los datos: ${e.localizedMessage}")
                }
            },
            { error ->
                onError("Error en la solicitud: ${error.message}")
            }
        )
        requestQueue.add(jsonArrayRequest)
    }

    override fun addTrackToAlbum(albumID: Int, track: Track, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val requestQueue = Volley.newRequestQueue(context)
        val url = "$baseUrl/$albumID/tracks"
        val trackJson = JSONObject(Gson().toJson(track))

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, trackJson,
            { _ ->
                onSuccess()
            },
            { error ->
                onError("Error en la solicitud: ${error.message}")
            }
        )
        requestQueue.add(jsonObjectRequest)
    }
}
