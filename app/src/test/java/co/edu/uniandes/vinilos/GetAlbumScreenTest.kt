import android.content.Context
import co.edu.uniandes.vinilos.model.models.Album
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

class AlbumViewModelTest {

    private val albumProvider = Mockito.mock(AlbumProvider::class.java)
    private val albumRepository = AlbumRepository(albumProvider)
    private lateinit var viewModel: AlbumViewModel
    val mockContext: Context = Mockito.mock(Context::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = AlbumViewModel(albumRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `albums are loaded correctly`() = runBlocking {
        val mockAlbumList = listOf(
            Album(name = "Album 1", releaseDate = "2022-01-01T00:00:00.000Z", cover = "", description = "", genre = "", recordLabel = ""),
            Album(name = "Album 2", releaseDate = "2023-01-01T00:00:00.000Z", cover = "", description = "", genre = "", recordLabel = "")
        )

        Mockito.doAnswer { invocation ->
            val onSuccess = invocation.getArgument<(List<Album>) -> Unit>(0)
            onSuccess(mockAlbumList)
            null
        }.`when`(albumProvider).getAlbums(any(), any())

        viewModel.loadAlbums(mockContext)

        assertEquals(mockAlbumList, viewModel.albums.first())
    }

    @Test
    fun `no albums message is shown when album list is empty`() = runBlocking {
        Mockito.doAnswer { invocation ->
            val onSuccess = invocation.getArgument<(List<Album>) -> Unit>(0)
            onSuccess(emptyList())
            null
        }.`when`(albumProvider).getAlbums(any(), any())

        viewModel.loadAlbums(mockContext)

        assertTrue(viewModel.albums.first().isEmpty())
    }

    @Test
    fun `recently added album is present after adding`() = runBlocking {
        val newAlbum = Album(name = "Nuevo Album", releaseDate = "2023-01-01T00:00:00.000Z", cover = "", description = "", genre = "", recordLabel = "")

        Mockito.doAnswer { invocation ->
            val onSuccess = invocation.getArgument<(List<Album>) -> Unit>(0)
            onSuccess(listOf(newAlbum))
            null
        }.`when`(albumProvider).getAlbums(any(), any())

        viewModel.loadAlbums(mockContext)

        assertEquals(listOf(newAlbum), viewModel.albums.first())
    }
}
