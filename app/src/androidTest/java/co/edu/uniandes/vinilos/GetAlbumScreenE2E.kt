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
class GetAlbumScreenE2ETest {

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
    fun testAddTrackButtonIsDisplayedForCollectorProfile() {
        composeTestRule.onNodeWithText("Coleccionista").performClick()

        val timeTaken = measureTimeMillis {
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithTag("AlbumItem").fetchSemanticsNodes().isNotEmpty()
            }

            composeTestRule.onAllNodesWithTag("AlbumItem").onFirst().performClick()

            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithTag("AgregarTrackButton").fetchSemanticsNodes().isNotEmpty()
            }

            composeTestRule.onNodeWithTag("AgregarTrackButton").assertIsDisplayed()
        }
        println("Tiempo para testAddTrackButtonIsDisplayedForCollectorProfile: $timeTaken ms")
    }

    @Test
    fun testAddTrackButtonIsNotDisplayedForUserProfile() {
        composeTestRule.onNodeWithText("Usuario").performClick()

        val timeTaken = measureTimeMillis {
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithTag("AlbumItem").fetchSemanticsNodes().isNotEmpty()
            }

            composeTestRule.onAllNodesWithTag("AlbumItem").onFirst().performClick()
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithTag("AgregarTrackButton").fetchSemanticsNodes().isEmpty()
            }

            composeTestRule.onAllNodesWithTag("AgregarTrackButton").assertCountEquals(0)
        }
        println("Tiempo para testAddTrackButtonIsNotDisplayedForUserProfile: $timeTaken ms")
    }

}
