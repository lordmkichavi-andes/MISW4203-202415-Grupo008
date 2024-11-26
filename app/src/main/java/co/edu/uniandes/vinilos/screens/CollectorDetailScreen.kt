package co.edu.uniandes.vinilos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.uniandes.vinilos.R
import co.edu.uniandes.vinilos.model.models.Collector
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.providers.AlbumProviderAPI
import co.edu.uniandes.vinilos.model.repository.AlbumRepository
import co.edu.uniandes.vinilos.viewmodel.Artist
import co.edu.uniandes.vinilos.ui.components.MainScreenWithBottomBar
import co.edu.uniandes.vinilos.viewmodel.AlbumViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CollectorDetailScreen(
    navController: NavController,
    collector: Collector,
    viewModel: AlbumViewModel = AlbumViewModel(AlbumRepository(AlbumProviderAPI(LocalContext.current)))
) {
    val context = LocalContext.current
    val albumIds = collector.collectorAlbums?.mapNotNull { it.id } ?: emptyList()
    viewModel.loadAlbumsByIds(context, albumIds)
    val albums by viewModel.albums.collectAsState()

    MainScreenWithBottomBar(
        navController = navController,
        initialTitle = "",
        onOptionSelected = { route -> navController.navigate(route) },
        showBackIcon = true,
        backDestination = "get_coleccionistas",
        profile = "Usuario"
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .testTag("ArtistDetailScreen")
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                CollectorDetailHeader(collector)
                Spacer(modifier = Modifier.height(16.dp))
                CollectorFavoriteArtistsSection(collector.favoritePerformers)
                Spacer(modifier = Modifier.height(16.dp))
                CollectorFavoriteAlbumesSection(albums)
            }
        }
    }
}

@Composable
fun CollectorDetailHeader(collector: Collector) {
    Text(
        text = collector.name,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(horizontal = 16.dp).testTag("CollectorName")
    )

    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        Icon(
            imageVector = Icons.Default.Phone,
            contentDescription = "Número de teléfono del coleccionista",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = collector.telephone ?: "Teléfono desconocido",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        Icon(
            imageVector = Icons.Filled.Email,
            contentDescription = "Correo electrónico del coleccionista",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = collector.email ?: "Email desconocido",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun CollectorFavoriteArtistsSection(artists: List<Artist>?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.Favorite,
            contentDescription = "Sección de artistas favoritos",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Artistas Favoritos",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.testTag("CollectorArtistsHeader")
        )
    }
    Spacer(modifier = Modifier.height(16.dp))

    if (artists.isNullOrEmpty()) {
        Text(
            text = "No se encontraron artistas favoritos.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(16.dp).testTag("EmptyArtistsMessage")
        )
    } else {
        artists.forEach { artist ->
            ArtistCardStatic(artist)
        }
    }
}

@Composable
fun ArtistCardStatic(artist: Artist) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("ArtistItem"),
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
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Fecha de nacimiento del artista",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = formatBirthDate(artist.birthDate),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun CollectorFavoriteAlbumesSection(albumes: List<Album>?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.Favorite,
            contentDescription = "Sección de álbumes favoritos",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Álbumes Favoritos",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.testTag("CollectorAlbumHeader")
        )
    }
    Spacer(modifier = Modifier.height(16.dp))

    if (albumes.isNullOrEmpty()) {
        Text(
            text = "No se encontraron álbumes favoritos.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(16.dp).testTag("EmptyArtistsMessage")
        )
    } else {
        albumes.forEach { album ->
            AlbumesCardStatic(album)
        }
    }
}

@Composable
fun AlbumesCardStatic(album: Album) {
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
            .height(80.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("AlbumItem"),
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
                contentDescription = "Portada del álbum ${album.name}",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 8.dp)
            )

            Column {
                Text(
                    text = album.name ?: "Título desconocido",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    fontSize = 16.sp
                )
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}
