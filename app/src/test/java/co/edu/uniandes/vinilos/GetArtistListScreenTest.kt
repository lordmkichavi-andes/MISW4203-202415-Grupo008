import android.content.Context
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.providers.ArtistProviderAPI
import co.edu.uniandes.vinilos.model.repository.AlbumRepository
import co.edu.uniandes.vinilos.model.repository.ArtistRepository
import co.edu.uniandes.vinilos.viewmodel.AlbumViewModel
import co.edu.uniandes.vinilos.viewmodel.Artist
import co.edu.uniandes.vinilos.viewmodel.ArtistViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class ArtistViewModelTest {

    private val artistProvider = Mockito.mock(ArtistProviderAPI::class.java)
    private val artistRepository = ArtistRepository(artistProvider)
    private lateinit var viewModel: ArtistViewModel
    val mockContext: Context = Mockito.mock(Context::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = ArtistViewModel(artistRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `artists are loaded correctly`() = runBlocking {
        val mockArtistList = listOf(
            Artist( name = "Artista 1", birthDate = "2022-01-01T00:00:00.000Z", description = "", image = "",),
            Artist( name = "Artista 2", birthDate = "2022-01-01T00:00:00.000Z", description = "", image = "",),
            )

        Mockito.doAnswer { invocation ->
            val onSuccess = invocation.getArgument<(List<Artist>) -> Unit>(0)
            onSuccess(mockArtistList)
            null
        }.`when`(artistProvider).getArtists(any(), any())

        viewModel.loadArtists()

        assertEquals(mockArtistList, viewModel.artists.first())
    }

    @Test
    fun `no artists message is shown when album list is empty`() = runBlocking {
        Mockito.doAnswer { invocation ->
            val onSuccess = invocation.getArgument<(List<Artist>) -> Unit>(0)
            onSuccess(emptyList())
            null
        }.`when`(artistProvider).getArtists(any(), any())

        viewModel.loadArtists()

        assertTrue(viewModel.artists.first().isEmpty())
    }


}
