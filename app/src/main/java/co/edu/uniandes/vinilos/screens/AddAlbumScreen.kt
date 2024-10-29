package co.edu.uniandes.vinilos.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.uniandes.vinilos.R
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.providers.AlbumProviderAPI
import co.edu.uniandes.vinilos.model.repository.AlbumRepository
import co.edu.uniandes.vinilos.viewmodel.AlbumViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun AddAlbumnScreen(navController: NavController, viewModel: AlbumViewModel = AlbumViewModel(AlbumRepository(AlbumProviderAPI(LocalContext.current)))) {

    LaunchedEffect(Unit) {
        val newAlbum = Album(
            name = "Buscando América3",
            cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
            releaseDate = "1984-08-01T00:00:00-05:00",
            description = "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984. La producción, bajo el sello Elektra, fusiona diferentes ritmos musicales tales como la salsa, reggae, rock, y el jazz latino. El disco fue grabado en Eurosound Studios en Nueva York entre mayo y agosto de 1983.",
            genre = "Salsa",
            recordLabel = "Elektra",
        )

        viewModel.addAlbum(newAlbum)
    }
    var items by remember { mutableStateOf(mutableListOf<String>()) }
    var newItem by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Vinilos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues -> // Padding para el contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Aplica padding del Scaffold
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Campo de texto para ingresar un nuevo elemento
            OutlinedTextField(
                value = newItem,
                onValueChange = { newItem = it },
                label = { Text("Nuevo Elemento") },
                modifier = Modifier.fillMaxWidth()
            )

            // Botón para agregar el nuevo elemento a la lista
            Button(
                onClick = {
                    if (newItem.isNotBlank()) {
                        items.add(newItem) // Agregar el nuevo elemento
                        newItem = "" // Limpiar el campo de texto
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Elemento")
            }

            // Lista de elementos
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(items) { item ->
                    Text(text = item, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}