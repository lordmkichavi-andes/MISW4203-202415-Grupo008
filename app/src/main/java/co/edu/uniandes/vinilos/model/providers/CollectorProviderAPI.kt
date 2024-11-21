package co.edu.uniandes.vinilos.model.providers

import android.content.Context
import co.edu.uniandes.vinilos.model.models.Collector
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CollectorProviderAPI(private val context: Context): CollectorProvider {
    private val path = "https://vynils-miso-7f8e65a403b6.herokuapp.com/collectors"

    override suspend fun getCollectors(page: Int, pageSize: Int): List<Collector> {
        val paginatedPath = "$path?page=$page&size=$pageSize"
        val requestQueue = Volley.newRequestQueue(context)

        return suspendCoroutine { continuation ->
            val jsonArrayRequest = JsonArrayRequest(
                Request.Method.GET, paginatedPath, null,
                { response ->
                    try {
                        val listType = object : TypeToken<List<Collector>>() {}.type
                        val artists: List<Collector> = Gson().fromJson(response.toString(), listType)
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