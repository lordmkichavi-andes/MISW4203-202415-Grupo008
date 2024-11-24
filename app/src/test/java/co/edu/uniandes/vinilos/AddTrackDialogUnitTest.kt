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
    fun `validate track name returns true for names with special characters`() {
        assertTrue(validateTrackName("Track-Name"))
        assertTrue(validateTrackName("Track_Name"))
        assertTrue(validateTrackName("Track Name 123"))
    }

    @Test
    fun `validate track duration returns true for valid duration`() {
        val result = validateTrackDuration("03:45")
        assertTrue(result)
    }

    @Test
    fun `validate track duration returns false for invalid format`() {
        assertFalse(validateTrackDuration("3:45"))
        assertFalse(validateTrackDuration("03:5"))
        assertFalse(validateTrackDuration("abc"))
        assertFalse(validateTrackDuration(""))
    }

    @Test
    fun `validate track duration returns false for invalid time values`() {
        assertFalse(validateTrackDuration("24:00"))
        assertFalse(validateTrackDuration("03:60"))
    }

    @Test
    fun `validate track duration returns true for edge cases`() {
        assertTrue(validateTrackDuration("00:00"))
        assertTrue(validateTrackDuration("23:59"))
    }

    @Test
    fun `validate track name handles long names`() {
        val longName = "a".repeat(256)
        assertTrue(validateTrackName(longName))
    }

    @Test
    fun `validate track duration handles random valid and invalid durations`() {
        val validDurations = listOf("01:30", "12:15", "22:45", "00:01")
        val invalidDurations = listOf("25:00", "12:60", "99:99", "1:5", "abc:def", "")

        validDurations.forEach {
            assertTrue("Expected valid duration: $it", validateTrackDuration(it))
        }

        invalidDurations.forEach {
            assertFalse("Expected invalid duration: $it", validateTrackDuration(it))
        }
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
