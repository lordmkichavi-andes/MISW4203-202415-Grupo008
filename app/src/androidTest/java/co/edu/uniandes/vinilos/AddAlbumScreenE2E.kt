package co.edu.uniandes.vinilos

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import kotlin.system.measureTimeMillis

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AddAlbumScreenE2E {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodes(hasText("Selecciona tu tipo de usuario para comenzar:")).fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Selecciona tu tipo de usuario para comenzar:")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Coleccionista").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithTag("AgregarAlbumButton").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @After
    fun tearDown() {
        composeTestRule.activity.finish()
    }

    @Test
    fun testA_SelectCollectorProfile() {
        val timeTaken = measureTimeMillis {
            composeTestRule.onNodeWithTag("AgregarAlbumButton")
                .assertIsDisplayed()
                .performClick()

            composeTestRule.onNodeWithTag("NombreField").performTextInput("Álbum de prueba")
            composeTestRule.onNodeWithTag("PortadaField").performTextInput("https://example.com/image.jpg")
            composeTestRule.onNodeWithTag("FechaField").performClick()
            composeTestRule.onNodeWithTag("DescripciónField").performTextInput("Descripción de prueba del álbum")

            composeTestRule.onNodeWithText("Agregar")
                .assertIsDisplayed()
                .performClick()
        }
        println("Tiempo para testA_SelectCollectorProfile: $timeTaken ms")
    }

    @Test
    fun testB_AlbumIsNotAddedWhenFieldsAreIncomplete() {
        val timeTaken = measureTimeMillis {
            composeTestRule.onNodeWithTag("AgregarAlbumButton").performClick()

            composeTestRule.onNodeWithTag("PortadaField").performTextInput("https://example.com/image.jpg")
            composeTestRule.onNodeWithTag("FechaField").performClick()
            composeTestRule.onNodeWithTag("DescripciónField").performTextInput("Descripción de prueba del álbum")

            composeTestRule.onNodeWithText("Agregar").assertIsNotEnabled()
        }
        println("Tiempo para testB_AlbumIsNotAddedWhenFieldsAreIncomplete: $timeTaken ms")
    }
}
