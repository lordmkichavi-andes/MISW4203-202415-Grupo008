package co.edu.uniandes.vinilos.model.providers

import android.content.Context
import co.edu.uniandes.vinilos.model.models.Album
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.reflect.TypeToken

import com.google.gson.Gson
import org.json.JSONObject


class AlbumProviderAPI(private val context: Context): AlbumProvider {
    val path = "https://vynils-miso-7f8e65a403b6.herokuapp.com/albums"

    override fun getAlbums(onSuccess: (List<Album>) -> Unit, onError: (String) -> Unit) {
        val requestQueue = Volley.newRequestQueue(context)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, path, null,
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

    override fun addAbum(album: Album, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val requestQueue = Volley.newRequestQueue(context)

        // Convertir el objeto Album a JSON usando Gson
        val albumJson = JSONObject(Gson().toJson(album))
        print(albumJson)

        // Crear la solicitud POST
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, path, albumJson,
            { response ->
                // Aquí puedes procesar la respuesta del servidor si es necesario
                onSuccess()
            },
            { error ->
                onError("Error en la solicitud: ${error.message}")
            }
        )

        requestQueue.add(jsonObjectRequest)
    }
}