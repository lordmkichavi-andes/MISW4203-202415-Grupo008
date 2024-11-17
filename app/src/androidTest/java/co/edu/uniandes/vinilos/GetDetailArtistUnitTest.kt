package co.edu.uniandes.vinilos

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.screens.ArtistDetailScreen
import co.edu.uniandes.vinilos.viewmodel.Artist
import org.junit.Rule
import org.junit.Test

class GetDetailArtistUnitTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testArtistDetailScreenDisplaysCorrectly() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        val artist = Artist(
            id = 1,
            name = "Jane Doe",
            image = "https://example.com/artist.jpg",
            birthDate = "1985-06-15T00:00:00.000Z",
            description = "Artista con varios álbumes.",
            albums = listOf(
                Album(
                    id = 1,
                    name = "Primer Álbum",
                    cover = "https://example.com/album1.jpg",
                    releaseDate = "2010-01-01",
                    description = "Descripción del primer álbum",
                    genre = "Rock",
                    recordLabel = "Discográfica A",
                    tracks = emptyList()
                ),
                Album(
                    id = 2,
                    name = "Segundo Álbum",
                    cover = "https://example.com/album2.jpg",
                    releaseDate = "2015-01-01",
                    description = "Descripción del segundo álbum",
                    genre = "Pop",
                    recordLabel = "Discográfica B",
                    tracks = emptyList()
                )
            )
        )

        composeTestRule.setContent {
            ArtistDetailScreen(navController = navController, artist = artist)
        }

        composeTestRule.onNodeWithText("Jane Doe").assertExists()
        composeTestRule.onNodeWithText("Primer Álbum").assertExists()
        composeTestRule.onNodeWithText("Segundo Álbum").assertExists()
        composeTestRule.onNodeWithText("2010-01-01").assertExists()
        composeTestRule.onNodeWithText("2015-01-01").assertExists()
    }

    @Test
    fun testArtistDetailScreenRendersWithoutErrors() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        val artist = Artist(
            id = 1,
            name = "Artista Prueba",
            image = "https://example.com/artist.jpg",
            birthDate = "1985-06-15T00:00:00.000Z",
            description = "Un artista de prueba sin álbumes.",
            albums = emptyList()
        )

        composeTestRule.setContent {
            ArtistDetailScreen(navController = navController, artist = artist)
        }
    }

    @Test
    fun testArtistDetailScreenWithAlbums() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        val artist = Artist(
            id = 1,
            name = "Artista Con Álbumes",
            image = "https://example.com/artist.jpg",
            birthDate = "1990-01-01T00:00:00.000Z",
            description = "Un artista con álbumes de prueba.",
            albums = listOf(
                Album(id = 1, name = "Álbum 1", cover = "https://example.com/album1.jpg", releaseDate = "2000-01-01", description = "", genre = "", recordLabel = "", tracks = emptyList()),
                Album(id = 2, name = "Álbum 2", cover = "https://example.com/album2.jpg", releaseDate = "2005-01-01", description = "", genre = "", recordLabel = "", tracks = emptyList())
            )
        )

        composeTestRule.setContent {
            ArtistDetailScreen(navController = navController, artist = artist)
        }

        composeTestRule.onNodeWithText("Artista Con Álbumes").assertExists()
        composeTestRule.onNodeWithText("Álbum 1").assertExists()
        composeTestRule.onNodeWithText("Álbum 2").assertExists()
    }
}