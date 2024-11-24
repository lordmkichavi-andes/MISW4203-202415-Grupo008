package co.edu.uniandes.vinilos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.uniandes.vinilos.R
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.ui.components.MainScreenWithBottomBar
import co.edu.uniandes.vinilos.viewmodel.Artist
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ArtistDetailScreen(
    navController: NavController,
    artist: Artist
) {
    MainScreenWithBottomBar(
        navController = navController,
        initialTitle = "Detalle del Artista",
        onOptionSelected = { route -> navController.navigate(route) },
        showBackIcon = true,
        backDestination = "get_artistas",
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
                ArtistDetailHeader(artist)
                Spacer(modifier = Modifier.height(16.dp))
                ArtistAlbumSection(artist.albums)
            }
        }
    }
}

@Composable
fun ArtistDetailHeader(artist: Artist) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(artist.image)
            .crossfade(true)
            .error(R.drawable.noartists)
            .placeholder(R.drawable.noartists)
            .build(),
        contentDescription = "Imagen del artista ${artist.name}",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .testTag("ArtistImage")
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = artist.name,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(horizontal = 16.dp).testTag("ArtistName")
    )

    Text(
        text = formatBirthDate(artist.birthDate),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 16.sp,
        modifier = Modifier.padding(horizontal = 16.dp).testTag("ArtistBirthDate")
    )

    Text(
        text = artist.description.takeIf { !it.isNullOrBlank() } ?: "Descripción no disponible",
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(16.dp).testTag("ArtistDescription")
    )
}

@Composable
fun ArtistAlbumSection(albums: List<Album>?) {
    Text(
        text = "Álbumes",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(horizontal = 16.dp).testTag("AlbumsHeader")
    )

    if (albums.isNullOrEmpty()) {
        Text(
            text = "No se encontraron álbumes para este artista.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(16.dp).testTag("EmptyAlbumsMessage")
        )
    } else {
        albums.forEach { album ->
            AlbumCardStatic(album)
        }
    }
}

@Composable
fun AlbumCardStatic(album: Album) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .testTag("AlbumCard"),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = album.cover,
                contentDescription = "Carátula del álbum ${album.name}",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 8.dp)
                    .testTag("AlbumImage")
            )
            Column {
                Text(
                    album.name,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.testTag("AlbumName")
                )
                Text(
                    album.releaseDate,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.testTag("AlbumReleaseDate")
                )
            }
        }
    }
}
