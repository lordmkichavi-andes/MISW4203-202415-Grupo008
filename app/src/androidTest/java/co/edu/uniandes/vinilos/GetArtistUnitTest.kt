package co.edu.uniandes.vinilos

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.edu.uniandes.vinilos.screens.ArtistItem
import co.edu.uniandes.vinilos.screens.formatBirthDate
import co.edu.uniandes.vinilos.viewmodel.Artist
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtistListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testFormatBirthDateReturnsCorrectly() {
        val formattedDate = formatBirthDate("1990-01-01T00:00:00.000Z")
        Assert.assertEquals("01/01/1990", formattedDate)

        val unknownDate = formatBirthDate(null)
        Assert.assertEquals("Fecha desconocida", unknownDate)

        val invalidDate = formatBirthDate("invalid-date")
        Assert.assertEquals("Fecha desconocida", invalidDate)
    }

    @Test
    fun testArtistItemDisplaysCorrectly() {
        composeTestRule.setContent {
            ArtistItem(
                artist = Artist(
                    id = 1,
                    name = "Nombre del Artista",
                    birthDate = "1990-01-01T00:00:00.000Z",
                    image = "https://example.com/image.jpg"
                ),
                onClick = {}
            )
        }

        composeTestRule.onNodeWithText("Nombre del Artista").assertExists()
        composeTestRule.onNodeWithText("01/01/1990").assertExists()
    }

}
