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
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class ArtistListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

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
    fun testArtistItemWithRandomData() {
        val randomName = generateRandomString(10)
        val randomDate = generateRandomDate()
        val randomImage = "https://example.com/${generateRandomString(5)}.jpg"

        composeTestRule.setContent {
            ArtistItem(
                artist = Artist(
                    id = Random.nextInt(1, 1000),
                    name = randomName,
                    birthDate = randomDate,
                    image = randomImage
                ),
                onClick = {}
            )
        }

        composeTestRule.onNodeWithText(randomName).assertExists()
        composeTestRule.onNodeWithText(formatBirthDate(randomDate)).assertExists()
    }

    private fun generateRandomString(length: Int): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }

    private fun generateRandomDate(): String {
        val validDates = listOf(
            "2020-12-12T12:00:00.000Z",
            "1990-01-01T00:00:00.000Z",
            "2001-09-11T08:46:00.000Z"
        )
        val invalidDates = listOf(
            "invalid-date",
            "",
            "9999-99-99T99:99:99.999Z"
        )

        return if (Random.nextBoolean()) {
            validDates.random()
        } else {
            invalidDates.random()
        }
    }
}
