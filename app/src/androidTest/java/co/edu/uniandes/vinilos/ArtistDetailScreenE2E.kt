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
class ArtistDetailScreenE2E {

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
    }

    @After
    fun tearDown() {
        Thread.sleep(1000)
        composeTestRule.activityRule.scenario.close()
    }

    @Test
    fun testAlbumCatalogIsDisplayedForVisitorProfile() {
        composeTestRule.onNodeWithText("Usuario").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodes(hasContentDescriptionExactly("Menú"))
                .fetchSemanticsNodes().isNotEmpty()
        }


        composeTestRule.onNodeWithContentDescription("Menú")
            .performClick()

        composeTestRule.onNodeWithText("Listado de artistas")
            .assertExists()

        composeTestRule.onNodeWithText("Listado de artistas").performClick()
        val timeTaken = measureTimeMillis {
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithTag("ArtistItem").fetchSemanticsNodes().isNotEmpty()
            }

            composeTestRule.onAllNodesWithTag("ArtistItem")
                .onFirst()
                .performClick()

            composeTestRule.onNodeWithText("Álbumes")
                .assertExists()
            
        }
        println("Tiempo para testArtistListIsDisplayedForVisitorProfile: $timeTaken ms")
    }

    @Test
    fun testAlbumCatalogIsDisplayedForCollectorProfile() {
        composeTestRule.onNodeWithText("Coleccionista").performClick()


        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodes(hasContentDescriptionExactly("Menú"))
                .fetchSemanticsNodes().isNotEmpty()
        }


        composeTestRule.onNodeWithContentDescription("Menú")
            .performClick()

        composeTestRule.onNodeWithText("Listado de artistas")
            .assertExists()

        composeTestRule.onNodeWithText("Listado de artistas").performClick()
        val timeTaken = measureTimeMillis {
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithTag("ArtistItem").fetchSemanticsNodes().isNotEmpty()
            }

            composeTestRule.onAllNodesWithTag("ArtistItem")
                .onFirst()
                .performClick()

            composeTestRule.onNodeWithText("Álbumes")
                .assertExists()
        }
        println("Tiempo para testArtistListIsDisplayedForVisitorProfile: $timeTaken ms")
    }

}
