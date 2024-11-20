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
class CollectorsListScreenE2E {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
        Thread.sleep(1000)
        composeTestRule.activityRule.scenario.close()
    }

    @Test
    fun testCollectorListIsDisplayedForVisitorProfile() {
        composeTestRule.activityRule.scenario.recreate()
        composeTestRule.activityRule.scenario.moveToState(Lifecycle.State.RESUMED)

        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodes(hasText("Selecciona tu tipo de usuario para comenzar:"))
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Selecciona tu tipo de usuario para comenzar:")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Usuario").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodes(hasContentDescriptionExactly("Menú"))
                .fetchSemanticsNodes().isNotEmpty()
        }


        composeTestRule.onNodeWithContentDescription("Menú")
            .performClick()

        composeTestRule.onNodeWithText("Listado de coleccionistas")
            .assertExists()

        composeTestRule.onNodeWithText("Listado de coleccionistas").performClick()
        val timeTaken = measureTimeMillis {
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithTag("CollectorItem").fetchSemanticsNodes().isNotEmpty()
            }

            composeTestRule.onAllNodesWithTag("CollectorItem")
                .onFirst()
                .assertIsDisplayed()
        }
        println("Tiempo para testArtistListIsDisplayedForVisitorProfile: $timeTaken ms")
    }

    @Test
    fun testCollectorListIsDisplayedForCollectorProfile() {
        composeTestRule.activityRule.scenario.recreate()
        composeTestRule.activityRule.scenario.moveToState(Lifecycle.State.RESUMED)

        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodes(hasText("Selecciona tu tipo de usuario para comenzar:"))
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Selecciona tu tipo de usuario para comenzar:")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Coleccionista").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodes(hasContentDescriptionExactly("Menú"))
                .fetchSemanticsNodes().isNotEmpty()
        }


        composeTestRule.onNodeWithContentDescription("Menú")
            .performClick()

        composeTestRule.onNodeWithText("Listado de coleccionistas")
            .assertExists()

        composeTestRule.onNodeWithText("Listado de coleccionistas").performClick()
        val timeTaken = measureTimeMillis {
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithTag("CollectorItem").fetchSemanticsNodes().isNotEmpty()
            }

            composeTestRule.onAllNodesWithTag("CollectorItem")
                .onFirst()
                .assertIsDisplayed()
        }
        println("Tiempo para testArtistListIsDisplayedForVisitorProfile: $timeTaken ms")
    }

}
