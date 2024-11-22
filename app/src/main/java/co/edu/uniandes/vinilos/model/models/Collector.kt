package co.edu.uniandes.vinilos.model.models

import android.os.Parcelable
import co.edu.uniandes.vinilos.viewmodel.Artist
import kotlinx.parcelize.Parcelize

@Parcelize
data class Collector (
    val id: Int? = null,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<Comment>? = null,
    val favoritePerformers: List<Artist>? = null,
    val collectorAlbums: List<Album>? = null
    ): Parcelable