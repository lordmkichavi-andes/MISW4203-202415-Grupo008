package co.edu.uniandes.vinilos

import MainSelectionScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import co.edu.uniandes.vinilos.ui.theme.VinilosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VinilosTheme {
                MainSelectionScreen()
            }
        }
    }
}