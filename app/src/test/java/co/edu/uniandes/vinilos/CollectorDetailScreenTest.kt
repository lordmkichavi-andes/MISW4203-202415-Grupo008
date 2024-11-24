import android.content.Context
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.models.Collector
import co.edu.uniandes.vinilos.viewmodel.Artist
import co.edu.uniandes.vinilos.model.providers.AlbumProvider
import co.edu.uniandes.vinilos.model.repository.AlbumRepository
import co.edu.uniandes.vinilos.viewmodel.AlbumViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any

class CollectorDetailScreenTest {

    private val albumProvider = Mockito.mock(AlbumProvider::class.java)
    private val albumRepository = AlbumRepository(albumProvider)
    private lateinit var viewModel: AlbumViewModel
    private lateinit var mockCollector: Collector
    val mockContext: Context = Mockito.mock(Context::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = AlbumViewModel(albumRepository)

        mockCollector = Collector(
            id = 1,
            name = "Mock Collector",
            telephone = "123-456-7890",
            email = "mock@collector.com",
            favoritePerformers = listOf(
                Artist(
                    id = 1,
                    name = "Mock Artist",
                    birthDate = "1980-01-01T00:00:00.000Z",
                    image = "",
                    description = "Mock artist description"
                )
            ),
            collectorAlbums = listOf(
                Album(
                    id = 1,
                    name = "Mock Album 1",
                    releaseDate = "2022-01-01T00:00:00.000Z",
                    cover = "",
                    description = "Album description",
                    genre = "Mock Genre",
                    recordLabel = "Mock Label"
                )
            )
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `collector details are loaded correctly`() = runBlocking {
        val mockAlbumList = listOf(
            Album(
                id = 1,
                name = "Mock Album 1",
                releaseDate = "2022-01-01T00:00:00.000Z",
                cover = "",
                description = "Album description",
                genre = "Mock Genre",
                recordLabel = "Mock Label"
            )
        )

        Mockito.doAnswer { invocation ->
            val onSuccess = invocation.getArgument<(List<Album>) -> Unit>(0)
            onSuccess(mockAlbumList)
            null
        }.`when`(albumProvider).getAlbums(any(), any())

        viewModel.loadAlbums(mockContext)

        val albums = viewModel.albums.first()
        assertEquals(mockAlbumList, albums)
        assertEquals(mockCollector.name, "Mock Collector")
        assertEquals(mockCollector.telephone, "123-456-7890")
        assertEquals(mockCollector.email, "mock@collector.com")
        assertTrue(mockCollector.favoritePerformers?.isNotEmpty() == true)
    }

    @Test
    fun `favorite artists are displayed correctly`() = runBlocking {
        val artists = mockCollector.favoritePerformers ?: emptyList()
        assertTrue(artists.isNotEmpty())
        assertEquals(artists[0].name, "Mock Artist")
        assertEquals(artists[0].birthDate, "1980-01-01T00:00:00.000Z")
    }

    @Test
    fun `collector albums are displayed correctly`() = runBlocking {
        val albums = mockCollector.collectorAlbums ?: emptyList()
        assertTrue(albums.isNotEmpty())
        assertEquals(albums[0].name, "Mock Album 1")
        assertEquals(albums[0].releaseDate, "2022-01-01T00:00:00.000Z")
    }
}
