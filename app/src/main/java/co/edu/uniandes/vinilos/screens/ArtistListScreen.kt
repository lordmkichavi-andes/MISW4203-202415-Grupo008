package co.edu.uniandes.vinilos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import co.edu.uniandes.vinilos.R
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
        backDestination = "get_albumes/Usuario/0",
        profile = "Usuario"
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF0F0F0))
        ) {
            when {
                isLoading -> {
                    LoadingContent()
                }
                errorMessage != null -> {
                    ErrorContent(errorMessage, onRetry = { viewModel.loadArtists() })
                }
                artists.isEmpty() -> {
                    EmptyContent()
                }
                else -> {
                    ArtistList(artists, navController)
                }
            }
        }
    }
}

@Composable
fun ArtistItem(artist: Artist, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("ArtistItem")
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(20.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(artist.image)
                    .crossfade(true)
                    .error(R.drawable.noartists)
                    .placeholder(R.drawable.noartists)
                    .build(),
                contentDescription = "Imagen del artista ${artist.name}",
                modifier = Modifier
                    .size(96.dp)
                    .padding(end = 20.dp)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = artist.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Fecha de nacimiento",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = formatBirthDate(artist.birthDate),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp
                    )
                }
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

@Composable
fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text("Cargando tus artistas de vinilos favoritos", color = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
fun ErrorContent(errorMessage: String?, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = errorMessage ?: "Error desconocido", color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry, modifier = Modifier.testTag("RetryButton")) {
            Text("Reintentar")
        }
    }
}

@Composable
fun EmptyContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("¡Ups! No se encontraron artistas...", color = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
fun ArtistList(artists: List<Artist>, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("ArtistList"),
        contentPadding = PaddingValues(top = 16.dp)
    ) {
        items(artists) { artist ->
            ArtistItem(artist = artist) {
                navController.currentBackStackEntry?.savedStateHandle?.set("artist", artist)
                navController.navigate("artist_detail")
            }
        }
    }
}
