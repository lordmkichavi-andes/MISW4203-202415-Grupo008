package co.edu.uniandes.vinilos

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.Lifecycle
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
        composeTestRule.activityRule.scenario.recreate()
        composeTestRule.activityRule.scenario.moveToState(Lifecycle.State.RESUMED)

        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodes(hasText("Selecciona tu tipo de usuario para comenzar:"))
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Selecciona tu tipo de usuario para comenzar:")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Coleccionista").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("AgregarAlbumButton")
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    @After
    fun tearDown() {
        Thread.sleep(1000)
        composeTestRule.activityRule.scenario.close()
    }

    @Test
    fun testAddAlbumSuccessfullyAsCollector() {
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
        println("Tiempo para testAddAlbumSuccessfullyAsCollector: $timeTaken ms")
    }

    @Test
    fun testAlbumAdditionFailsWithIncompleteFields() {
        val timeTaken = measureTimeMillis {
            composeTestRule.onNodeWithTag("AgregarAlbumButton").performClick()
            composeTestRule.onNodeWithTag("PortadaField").performTextInput("https://example.com/image.jpg")
            composeTestRule.onNodeWithTag("FechaField").performClick()
            composeTestRule.onNodeWithTag("DescripciónField").performTextInput("Descripción de prueba del álbum")
            composeTestRule.onNodeWithText("Agregar").assertIsNotEnabled()
        }
        println("Tiempo para testAlbumAdditionFailsWithIncompleteFields: $timeTaken ms")
    }
}
