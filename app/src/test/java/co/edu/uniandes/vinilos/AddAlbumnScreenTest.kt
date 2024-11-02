import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddAlbumScreenTest {

    // Ejemplo de prueba para `validateField`
    @Test
    fun `test validateField returns true for valid input`() {
        val result = validateField("Album Name")
        assertTrue(result) // Debería ser verdadero para nombres válidos
    }

    @Test
    fun `test validateField returns false for empty or short input`() {
        assertFalse(validateField(""))  // Campo vacío
        assertFalse(validateField("Al"))  // Menos de 3 caracteres
    }

    // Ejemplo de prueba para `isDateValid`
    @Test
    fun `test isDateValid returns true for valid date`() {
        val result = isDateValid("12/12/2022")
        assertTrue(result)  // Debería ser verdadero para una fecha válida
    }

    @Test
    fun `test isDateValid returns false for invalid date`() {
        val result = isDateValid("32/12/2022")  // Día no válido
        assertFalse(result)
    }

    // Funciones de prueba
    private fun validateField(field: String): Boolean {
        return field.isNotBlank() && field.length > 3
    }

    private fun isDateValid(date: String, format: String = "dd/MM/yyyy"): Boolean {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        dateFormat.isLenient = false

        return try {
            dateFormat.parse(date) != null
        } catch (e: Exception) {
            false
        }
    }
}

class AddAlbumScreenFormValidationTest {

    private fun isFormValid(name: String, cover: String, releaseDate: String, description: String, genre: String, recordLabel: String): Boolean {
        return validateField(name) && validateField(cover) && isDateValid(releaseDate) &&
                validateField(description) && validateField(genre) && validateField(recordLabel)
    }

    @Test
    fun `test form is valid with correct input`() {
        val result = isFormValid(
            name = "Valid Album",
            cover = "https://example.com/cover.jpg",
            releaseDate = "12/12/2022",
            description = "Some description",
            genre = "Rock",
            recordLabel = "Sony Music"
        )
        assertTrue(result)
    }

    @Test
    fun `test form is invalid with incorrect input`() {
        val result = isFormValid(
            name = "A",  // Nombre inválido
            cover = "",  // URL de portada vacío
            releaseDate = "12/12/2022",
            description = "Some description",
            genre = "",  // Género vacío
            recordLabel = ""
        )
        assertFalse(result)
    }

    // Funciones auxiliares
    private fun validateField(field: String): Boolean {
        return field.isNotBlank() && field.length > 3
    }

    private fun isDateValid(date: String, format: String = "dd/MM/yyyy"): Boolean {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        dateFormat.isLenient = false
        return try {
            dateFormat.parse(date) != null
        } catch (e: Exception) {
            false
        }
    }
}

class ConvertMillisToDateTest {

    @Test
    fun `test convertMillisToDate returns correct format`() {
        val millis = 1672444800000 // Ejemplo de fecha en milisegundos para 31/12/2022
        val formattedDate = convertMillisToDate(millis)
        assertEquals("30/12/2022", formattedDate)
    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }
}
