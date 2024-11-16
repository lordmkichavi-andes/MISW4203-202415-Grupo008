package co.edu.uniandes.vinilos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
        onOptionSelected = { route ->
            navController.navigate(route)
        },
        showBackIcon = true,
        backDestination = "get_artistas",
        profile = "Usuario"
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
                .padding(paddingValues)
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(artist.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Imagen del artista",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = artist.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Text(
                    text = formatBirthDate(artist.birthDate),
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Text(
                    text = artist.description ?: "Descripción no disponible",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Álbumes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                artist.albums?.forEach { album ->
                    AlbumCardStatic(album)
                }
            }
        }
    }
}

@Composable
fun AlbumCardStatic(album: Album) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEFEF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = album.cover,
                contentDescription = "Carátula del álbum",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 8.dp)
            )
            Column {
                Text(album.name, fontWeight = FontWeight.Bold)
                Text(album.releaseDate, color = Color.Gray)
            }
        }
    }
}
