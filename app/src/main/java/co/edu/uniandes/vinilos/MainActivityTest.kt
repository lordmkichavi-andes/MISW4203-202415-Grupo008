package co.edu.uniandes.vinilos

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testCompleteProfileSelectionFlow() {
        composeTestRule.onNodeWithText("¡Hola!").assertIsDisplayed()
        composeTestRule.onNodeWithText("Usuario").performClick()
        composeTestRule.onNodeWithText("Perfil seleccionado: Usuario").assertIsDisplayed()
        composeTestRule.onNodeWithText("Coleccionista").performClick()
        composeTestRule.onNodeWithText("Perfil seleccionado: Coleccionista").assertIsDisplayed()
    }
}
