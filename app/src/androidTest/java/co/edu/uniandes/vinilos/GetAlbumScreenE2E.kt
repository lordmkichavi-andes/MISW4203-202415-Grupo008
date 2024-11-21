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
    fun testAddTrackButtonIsVisibleForCollectorProfile() {
        composeTestRule.onNodeWithText("Coleccionista").performClick()
        val timeTaken = measureTimeMillis {
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithTag("AlbumItem").fetchSemanticsNodes().isNotEmpty()
            }
            composeTestRule.onAllNodesWithTag("AlbumItem").onFirst().performClick()
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                try {
                    composeTestRule.onNodeWithTag("AddTrackButton").assertExists()
                    true
                } catch (e: AssertionError) {
                    false
                }
            }
            composeTestRule.onNodeWithTag("AddTrackButton").assertIsDisplayed()
            composeTestRule.onNodeWithTag("AddTrackButton").performClick()
            composeTestRule.onNodeWithText("Agregar Track").assertIsDisplayed()
        }
        println("Tiempo para testAddTrackButtonIsDisplayedForCollectorProfile: $timeTaken ms")
    }


    @Test
    fun testAddTrackButtonIsNotVisibleForUserProfile() {
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

    @Test
    fun testAddTrackButtonDoesNotExistForUserProfile() {
        composeTestRule.onNodeWithText("Usuario").performClick()

        val timeTaken = measureTimeMillis {
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithTag("AlbumItem").fetchSemanticsNodes().isNotEmpty()
            }

            composeTestRule.onAllNodesWithTag("AlbumItem").onFirst().performClick()

            composeTestRule.waitUntil(timeoutMillis = 10000) {
                try {
                    composeTestRule.onNodeWithTag("AddTrackButton").assertDoesNotExist()
                    true
                } catch (e: AssertionError) {
                    false
                }
            }

            composeTestRule.onNodeWithTag("AddTrackButton").assertDoesNotExist()
        }

        println("Tiempo para testAddTrackButtonIsNotDisplayedForUserProfile: $timeTaken ms")
    }


    @Test
    fun testAlbumListDisplaysAndNavigatesToDetails() {
        composeTestRule.onNodeWithText("Coleccionista").performClick()

        val timeTaken = measureTimeMillis {
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithTag("AlbumItem").fetchSemanticsNodes().isNotEmpty()
            }

            val albumNodes = composeTestRule.onAllNodesWithTag("AlbumItem").fetchSemanticsNodes()
            Assert.assertTrue("Se esperaba al menos un álbum, pero no se encontró ninguno.", albumNodes.isNotEmpty())

            composeTestRule.onAllNodesWithTag("AlbumItem").onFirst().performClick()

            composeTestRule.waitUntil(timeoutMillis = 10000) {
                try {
                    composeTestRule.onNodeWithTag("AlbumTitle").assertExists()
                    true
                } catch (e: AssertionError) {
                    false
                }
            }

            composeTestRule.onNodeWithTag("AlbumTitle").assertIsDisplayed()
        }

        println("Tiempo para testAlbumListDisplaysAndNavigatesToDetails: $timeTaken ms")
    }
}
