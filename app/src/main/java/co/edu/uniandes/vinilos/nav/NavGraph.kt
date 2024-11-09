package co.edu.uniandes.vinilos.nav

import MainSelectionScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.edu.uniandes.vinilos.screens.AddAlbumnScreen
import co.edu.uniandes.vinilos.screens.ArtistListScreen
import co.edu.uniandes.vinilos.screens.GetAlbumesScreen

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
        composable("get_artistas") {
            ArtistListScreen(navController = navController)
        }
    }
}
