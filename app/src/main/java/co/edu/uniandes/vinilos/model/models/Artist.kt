package co.edu.uniandes.vinilos.viewmodel

import co.edu.uniandes.vinilos.model.models.Album
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import android.os.Parcelable

@Parcelize
data class Artist(
    val id: Int? = null,
    val name: String,
    val birthDate: String? = null,
    val description: String? = null,
    val image: String? = null,
    val albums: @RawValue List<Album>? = null
) : Parcelable
