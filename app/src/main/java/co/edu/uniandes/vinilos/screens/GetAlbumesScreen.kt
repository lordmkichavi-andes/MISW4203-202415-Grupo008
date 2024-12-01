package co.edu.uniandes.vinilos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Error
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAlbumesScreen(
    navController: NavController,
    profile: String,
    recentlyAddedAlbum: Boolean? = false,
    viewModel: AlbumViewModel = AlbumViewModel(AlbumRepository(AlbumProviderAPI(LocalContext.current)))
) {
    val context = LocalContext.current
    viewModel.loadAlbums(context)
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
                    .semantics { contentDescription = "Cargando álbumes de vinilos" },
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
                            contentDescription = "Sin álbumes",
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.Start
                ) {
                    if (profile == "Coleccionista") {
                        AddAlbumButton(navController)
                    }
                    if (recentlyAddedAlbum == true) {
                        Spacer(modifier = Modifier.height(10.dp))
                        InformationBackgroundText(text = "Álbum agregado correctamente")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    AlbumList(
                        albums = albums,
                        modifier = Modifier
                            .fillMaxSize(),
                        navController, profile
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
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .padding(8.dp)
            .testTag("AgregarAlbumButton")
            .semantics { contentDescription = "Botón para agregar un álbum" }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Agregar álbum",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun AlbumList(albums: List<Album>, modifier: Modifier = Modifier, navController: NavController, profile: String) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(albums) { album ->
            AlbumCard(album = album, navController, profile)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun AlbumCard(album: Album, navController: NavController, profile: String) {
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
            .testTag("AlbumItem")
            .clickable {
                navController.navigate("get_album/${profile}/${album.id}")
            },
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
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = formattedDate,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun InformationBackgroundText(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(140.dp)
            .testTag("AddedAlbum")
            .padding(horizontal = 16.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center
        )
    }
}
