package co.edu.uniandes.vinilos.nav

import MainSelectionScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.edu.uniandes.vinilos.model.models.Collector
import co.edu.uniandes.vinilos.screens.AddAlbumnScreen
import co.edu.uniandes.vinilos.screens.ArtistDetailScreen
import co.edu.uniandes.vinilos.screens.GetAlbumScreen
import co.edu.uniandes.vinilos.screens.ArtistListScreen
import co.edu.uniandes.vinilos.screens.CollectorsListScreen
import co.edu.uniandes.vinilos.screens.GetAlbumesScreen
import co.edu.uniandes.vinilos.screens.CollectorDetailScreen
import co.edu.uniandes.vinilos.viewmodel.Artist

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            MainSelectionScreen(navController) // Pantalla de selección perfil
        }
        composable("add_album") {
            AddAlbumnScreen(navController)
        }
        composable("get_albumes/{profile}/{added_album}") { backStackEntry ->
            val profile = backStackEntry.arguments?.getString("profile")
            val addedAlbum = backStackEntry.arguments?.getString("added_album")
            if (profile != null && addedAlbum != null && addedAlbum == "1") {
                GetAlbumesScreen(navController, profile = profile, recentlyAddedAlbum = true)
            } else if (profile != null ){
                GetAlbumesScreen(navController, profile = profile)
            }
        }
        composable("get_album/{profile}/{album_id}") { backStackEntry ->
            val profile = backStackEntry.arguments?.getString("profile")
            val album_id = backStackEntry.arguments?.getString("album_id")
            if (profile != null && album_id != null) {
                GetAlbumScreen(navController, profile = profile, albumID = album_id)
            } else if (profile != null ){
                GetAlbumesScreen(navController, profile = profile)
            }
        }
        composable("get_artistas") {
            ArtistListScreen(navController = navController)
        }
        composable("artist_detail") { backStackEntry ->
            val artist = navController.previousBackStackEntry?.savedStateHandle?.get<Artist>("artist")
            artist?.let {
                ArtistDetailScreen(navController = navController, artist = it)
            }
        }
        composable("collector_detail") { backStackEntry ->
            val collector = navController.previousBackStackEntry?.savedStateHandle?.get<Collector>("collector")
            collector?.let {
                CollectorDetailScreen(navController = navController, collector = it)
            }
        }
        composable("get_coleccionistas") {
            CollectorsListScreen(navController = navController)
        }

    }
}
