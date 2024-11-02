package co.edu.uniandes.vinilos

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetAlbumCatalogE2ETest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodes(hasText("Selecciona tu tipo de usuario para comenzar:")).fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Usuario").performClick()
    }

    @After
    fun tearDown() {
        composeTestRule.activityRule.scenario.recreate()
    }

    @Test
    fun testAlbumCatalogIsDisplayedForVisitorProfile() {
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("AlbumItem").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onAllNodesWithTag("AlbumItem")
            .onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun testAlbumLoadingMessageIsDisplayed() {
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("Cargando tus álbumes de vinilos favoritos.").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Cargando tus álbumes de vinilos favoritos.")
            .assertIsDisplayed()
    }
}
