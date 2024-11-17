import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.edu.uniandes.vinilos.screens.AddAlbumnScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddAlbumScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAddAlbumScreenDisplaysFieldsAndSubmitButton() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        composeTestRule.setContent {
            AddAlbumnScreen(navController = navController)
        }

        composeTestRule.onNodeWithText("Nombre").performTextInput("Album de prueba")
        composeTestRule.onNodeWithText("Portada").performTextInput("https://example.com/image.jpg")
        composeTestRule.onNodeWithText("Descripción").performTextInput("Este es un album de prueba")

        composeTestRule.onNodeWithText("Género").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodes(hasText("")).onFirst().performClick()

        composeTestRule.onNodeWithText("Sello discográfico").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodes(hasText("")).onFirst().performClick()

        composeTestRule.onNodeWithText("Agregar").assertIsNotEnabled().performClick()
    }
}
