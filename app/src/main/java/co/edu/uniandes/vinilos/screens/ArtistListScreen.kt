package co.edu.uniandes.vinilos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import co.edu.uniandes.vinilos.model.providers.ArtistProviderAPI
import co.edu.uniandes.vinilos.model.repository.ArtistRepository
import co.edu.uniandes.vinilos.ui.components.MainScreenWithBottomBar
import co.edu.uniandes.vinilos.viewmodel.Artist
import co.edu.uniandes.vinilos.viewmodel.ArtistViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ArtistListScreen(navController: NavController) {
    val context = LocalContext.current
    val artistRepository = remember { ArtistRepository(ArtistProviderAPI(context)) }
    val viewModel = remember { ArtistViewModel(artistRepository) }

    val artists by viewModel.artists.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    MainScreenWithBottomBar(
        navController = navController,
        initialTitle = "Listado de artistas",
        onOptionSelected = { route -> navController.navigate(route) },
        showBackIcon = true,
        backDestination = "login",
        profile = "Usuario"
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF0F0F0)),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Cargando tus artistas de vinilos favoritos")
                    }
                }
                errorMessage != null -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = errorMessage ?: "Error desconocido")
                        Button(onClick = { viewModel.loadArtists() }) {
                            Text("Reintentar")
                        }
                    }
                }
                artists.isEmpty() -> {
                    Text("¡Ups! No se encontraron artistas...")
                }
                else -> {
                    LazyColumn {
                        items(artists) { artist ->
                            ArtistItem(artist)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArtistItem(artist: Artist) {
    val formattedDate = formatBirthDate(artist.birthDate)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .height(80.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEFEF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(artist.image)
                    .crossfade(true)
                    .build(),
                contentDescription = "Imagen de artista",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 8.dp)
            )
            Column {
                Text(
                    text = artist.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = formattedDate,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = artist.description ?: "Descripción no disponible",
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

fun formatBirthDate(birthDate: String?): String {
    return if (birthDate != null) {
        try {
            val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val targetFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = originalFormat.parse(birthDate)
            date?.let { targetFormat.format(it) } ?: "Fecha desconocida"
        } catch (e: Exception) {
            "Fecha desconocida"
        }
    } else {
        "Fecha desconocida"
    }
}
