package co.edu.uniandes.vinilos

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SelectCollectorTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testSelectCollectorProfile() {
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodes(hasText("Selecciona tu tipo de usuario para comenzar:")).fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Selecciona tu tipo de usuario para comenzar:")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Coleccionista").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithTag("AgregarAlbumButton").fetchSemanticsNodes().isNotEmpty()
        }

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
}
