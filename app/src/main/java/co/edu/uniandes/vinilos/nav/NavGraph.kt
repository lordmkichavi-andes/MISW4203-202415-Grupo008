package co.edu.uniandes.vinilos.nav

import MainSelectionScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.edu.uniandes.vinilos.screens.AddAlbumnScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            MainSelectionScreen(navController) // Pantalla de selección perfil
        }
        composable("add_album") {
            AddAlbumnScreen(navController)
        }
    }
}