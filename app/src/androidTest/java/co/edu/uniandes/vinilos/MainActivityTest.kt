package co.edu.uniandes.vinilos

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
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
