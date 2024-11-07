package co.edu.uniandes.vinilos.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.edu.uniandes.vinilos.model.providers.AlbumProviderAPI
import co.edu.uniandes.vinilos.model.repository.AlbumRepository
import co.edu.uniandes.vinilos.ui.components.MainScreenWithBottomBar
import co.edu.uniandes.vinilos.viewmodel.AlbumViewModel

@Composable
fun GetAlbumScreen(
    navController: NavController,
    profile:String,
    albumID: String?,
    viewModel: AlbumViewModel = AlbumViewModel(AlbumRepository(AlbumProviderAPI(LocalContext.current)))
)
{
    val context = LocalContext.current

    if (albumID != null) {
        viewModel.getAlbum(context, albumID.toInt())
    }
    val album by viewModel.album.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    MainScreenWithBottomBar(
        navController = navController,
        initialTitle = "Catálogo de Álbumes",
        onOptionSelected = { route ->
            navController.navigate(route)
        },
        showBackIcon = false,
        backDestination = "",
        profile = profile
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Cargando tus álbumes de vinilos favoritos.")
                }
            }
        } else {

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.Start
                ) {

                      println(album)
            }
        }
    }
}
