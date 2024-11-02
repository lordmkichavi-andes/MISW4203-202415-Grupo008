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
class GetAlbumCatalogE2ETest {

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
        composeTestRule.onNodeWithText("Usuario").performClick()
    }

    @After
    fun tearDown() {
        Thread.sleep(1000)
        composeTestRule.activityRule.scenario.close()
    }

    @Test
    fun testAlbumCatalogIsDisplayedForVisitorProfile() {
        val timeTaken = measureTimeMillis {
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithTag("AlbumItem").fetchSemanticsNodes().isNotEmpty()
            }

            composeTestRule.onAllNodesWithTag("AlbumItem")
                .onFirst()
                .assertIsDisplayed()
        }
        println("Tiempo para testAlbumCatalogIsDisplayedForVisitorProfile: $timeTaken ms")
    }

    @Test
    fun testAlbumLoadingMessageIsDisplayed() {
        val timeTaken = measureTimeMillis {
            composeTestRule.waitUntil(timeoutMillis = 5000) {
                composeTestRule.onAllNodesWithText("Cargando tus álbumes de vinilos favoritos.")
                    .fetchSemanticsNodes().isNotEmpty()
            }

            composeTestRule.onNodeWithText("Cargando tus álbumes de vinilos favoritos.")
                .assertIsDisplayed()
        }
        println("Tiempo para testAlbumLoadingMessageIsDisplayed: $timeTaken ms")
    }
}
