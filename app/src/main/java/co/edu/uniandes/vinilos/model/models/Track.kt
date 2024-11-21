package co.edu.uniandes.vinilos.model.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
    val id: Int? = null,
    val name: String,
    val duration: String
) : Parcelable