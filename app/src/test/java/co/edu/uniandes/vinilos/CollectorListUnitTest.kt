package co.edu.uniandes.vinilos

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.edu.uniandes.vinilos.model.models.Collector
import co.edu.uniandes.vinilos.screens.CollectorItem

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectorsUnitTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCollectorItemDisplaysCorrectly() {
        composeTestRule.setContent {
            CollectorItem(
                collector = Collector(
                    id = 1,
                    name = "Nombre del coleccionista",
                    telephone = "3502457896",
                    email = "manollo@caracol.com.co"
                ),
                onClick = {}
            )
        }

        composeTestRule.onNodeWithText("Nombre del coleccionista").assertExists()
    }
}
