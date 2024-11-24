package co.edu.uniandes.vinilos.model.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment (
    val id: Int? = null,
    val description: String,
    val rating: Int
): Parcelable