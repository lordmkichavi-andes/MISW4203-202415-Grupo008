package co.edu.uniandes.vinilos.model.providers

import android.content.Context
import co.edu.uniandes.vinilos.viewmodel.Artist
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ArtistProviderAPI(private val context: Context) {
    private val path = "https://vynils-miso-7f8e65a403b6.herokuapp.com/musicians"

    fun getArtists(onSuccess: (List<Artist>) -> Unit, onError: (String) -> Unit) {
        val requestQueue = Volley.newRequestQueue(context)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, path, null,
            { response ->
                try {
                    val listType = object : TypeToken<List<Artist>>() {}.type
                    val artists: List<Artist> = Gson().fromJson(response.toString(), listType)
                    onSuccess(artists)
                } catch (e: Exception) {
                    onError("Error parsing data: ${e.localizedMessage}")
                }
            },
            { error ->
                onError("Request error: ${error.message}")
            }
        )
        requestQueue.add(jsonArrayRequest)
    }

    suspend fun getArtistsPage(page: Int, pageSize: Int): List<Artist> {
        val paginatedPath = "$path?page=$page&size=$pageSize"
        val requestQueue = Volley.newRequestQueue(context)

        return suspendCoroutine { continuation ->
            val jsonArrayRequest = JsonArrayRequest(
                Request.Method.GET, paginatedPath, null,
                { response ->
                    try {
                        val listType = object : TypeToken<List<Artist>>() {}.type
                        val artists: List<Artist> = Gson().fromJson(response.toString(), listType)
                        continuation.resume(artists)
                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                },
                { error ->
                    continuation.resumeWithException(Exception(error.message))
                }
            )
            requestQueue.add(jsonArrayRequest)
        }
    }
}
