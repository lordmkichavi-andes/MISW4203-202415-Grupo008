package co.edu.uniandes.vinilos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.viewmodel.AlbumViewModel
import co.edu.uniandes.vinilos.model.repository.AlbumRepository
import co.edu.uniandes.vinilos.model.providers.AlbumProviderAPI
import java.text.SimpleDateFormat
import java.util.*
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.foundation.lazy.items
import co.edu.uniandes.vinilos.ui.components.MainScreenWithBottomBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdUnits
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Error
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAlbumesScreen(navController: NavController, profile:String, viewModel: AlbumViewModel = AlbumViewModel(AlbumRepository(AlbumProviderAPI(LocalContext.current)))) {
    viewModel.loadAlbums()
    val albums by viewModel.albums.collectAsState(initial = emptyList())
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
                    .fillMaxSize()
                    .padding(paddingValues),
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
            if (albums.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "No albums",
                            modifier = Modifier.size(100.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "¡Ups! No se encontraron álbumes...")
                    }
                }
                if (profile == "Coleccionista") {
                    AddAlbumButton(navController)
                }

            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.TopStart
                ) {
                    if (profile == "Coleccionista") {
                        AddAlbumButton(navController)
                    }
                    // Muestra la lista de álbumes cuando no está vacía
                    AlbumList(
                        albums = albums,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    )
                }
            }
        }
    }
    }

@Composable
private fun AddAlbumButton(navController: NavController) {
    Button(
        onClick = { navController.navigate("add_album") },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent
        ),
        modifier = Modifier
            .padding(8.dp)
            .testTag("AgregarAlbumButton")
    ) {
        Box(
            modifier = Modifier
                .size(40.dp) // Define el tamaño del cuadrado
                .background(Color.White, shape = RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add, // Cambia el ícono si es necesario
                contentDescription = "Agregar",
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el icono y el texto
        Text(
            text = "Agregar álbum",
            color = Color.Black
        )
    }
}

@Composable
fun AlbumList(albums: List<Album>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(albums) { album ->
            AlbumCard(album = album)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun AlbumCard(album: Album) {
    val formattedDate = try {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val targetFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = originalFormat.parse(album.releaseDate)
        date?.let { targetFormat.format(it) } ?: "Fecha desconocida"
    } catch (e: Exception) {
        "Fecha desconocida"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(album.cover)
                    .crossfade(true)
                    .build(),
                contentDescription = "Imagen de álbum",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 8.dp)
            )

            Column {
                Text(
                    text = album.name ?: "Título desconocido",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = formattedDate,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}