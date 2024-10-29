package co.edu.uniandes.vinilos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import co.edu.uniandes.vinilos.nav.NavGraph
import co.edu.uniandes.vinilos.ui.theme.VinilosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VinilosTheme {
                // Inicializa el NavController
                val navController = rememberNavController()
                // Configura el NavHost
                NavGraph(navController)

            }
        }
    }
}