import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AddTrackDialogUnitTest {

    @Test
    fun `validate track name returns true for valid name`() {
        val result = validateTrackName("Track Name")
        assertTrue(result)
    }

    @Test
    fun `validate track name returns false for empty or blank name`() {
        assertFalse(validateTrackName(""))
        assertFalse(validateTrackName("  "))
    }

    @Test
    fun `validate track duration returns true for valid duration`() {
        val result = validateTrackDuration("03:45")
        assertTrue(result)
    }

    @Test
    fun `validate track duration returns false for invalid format`() {
        assertFalse(validateTrackDuration("3:45"))   // Falta un dígito en las horas
        assertFalse(validateTrackDuration("03:5"))  // Falta un dígito en los minutos
        assertFalse(validateTrackDuration("abc"))   // Letras no válidas
        assertFalse(validateTrackDuration(""))      // Vacío
    }

    @Test
    fun `validate track duration returns false for invalid time values`() {
        assertFalse(validateTrackDuration("24:00")) // Horas fuera de rango
        assertFalse(validateTrackDuration("03:60")) // Minutos fuera de rango
    }

    private fun validateTrackName(trackName: String): Boolean {
        return trackName.isNotBlank()
    }

    private fun validateTrackDuration(duration: String): Boolean {
        val regex = Regex("^\\d{2}:\\d{2}\$")
        if (!duration.matches(regex)) return false

        val parts = duration.split(":").map { it.toInt() }
        val hours = parts[0]
        val minutes = parts[1]
        return hours in 0..23 && minutes in 0..59
    }
}
