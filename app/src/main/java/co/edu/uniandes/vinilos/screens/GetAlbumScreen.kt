package co.edu.uniandes.vinilos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.uniandes.vinilos.model.models.Track
import co.edu.uniandes.vinilos.model.providers.AlbumProviderAPI
import co.edu.uniandes.vinilos.model.repository.AlbumRepository
import co.edu.uniandes.vinilos.ui.components.MainScreenWithBottomBar
import co.edu.uniandes.vinilos.viewmodel.AlbumViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun GetAlbumScreen(
    navController: NavController,
    profile: String,
    albumID: String?,
    viewModel: AlbumViewModel = AlbumViewModel(AlbumRepository(AlbumProviderAPI(LocalContext.current)))
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }

    if (!albumID.isNullOrEmpty()) {
        viewModel.getAlbum(context, albumID.toInt())
        viewModel.loadTracks(context, albumID.toInt())
    } else {
        navController.navigateUp()
        return
    }

    val album by viewModel.album.collectAsState()
    val tracks by viewModel.tracks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val message by viewModel.message.collectAsState()

    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val targetFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = album.releaseDate?.let {
        try {
            originalFormat.parse(it)?.let { parsedDate -> targetFormat.format(parsedDate) }
        } catch (e: Exception) {
            null
        }
    } ?: "Fecha desconocida"

    MainScreenWithBottomBar(
        navController = navController,
        initialTitle = "Detalle del Álbum",
        onOptionSelected = { route ->
            navController.navigate(route)
        },
        showBackIcon = true,
        backDestination = "get_albumes/$profile/0",
        profile = profile
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Cargando tu álbum de vinilos favorito.")
                }
            }
        } else if (album.name.isNullOrEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(scrollState)
                    .padding(top = 50.dp, bottom = 50.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(250.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "¡Ups! No eres tú, somos nosotros...",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Ha ocurrido un problema al obtener tu álbum favorito",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { navController.navigate("get_album/$profile/$albumID") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(text = "Intentar nuevamente", fontSize = 18.sp)
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(scrollState)
                    .padding(16.dp)
                    .padding(top = 50.dp, bottom = 50.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(album.cover)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Portada del álbum ${album.name}",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = album.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.testTag("AlbumTitle")
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = date,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Icon(
                        imageVector = Icons.Filled.MusicNote,
                        contentDescription = "Nota Musical",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = album.genre ?: "Género desconocido",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = album.description ?: "Sin descripción",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Album,
                        contentDescription = "Icono de un disco",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = album.recordLabel ?: "Sello desconocido",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (profile == "Coleccionista") {
                    Text(
                        text = "+ Agregar track",
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showDialog = true }
                            .padding(horizontal = 10.dp)
                            .testTag("AddTrackButton"),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Text(
                    text = "Tracks",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                TrackList(tracks = tracks)

                if (message.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = message,
                        color = if (message.contains("exitosamente", true)) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        modifier = Modifier.testTag("FeedbackMessage")
                    )
                }
            }
        }

        if (showDialog) {
            AddTrackDialog(
                albumId = albumID!!.toInt(),
                viewModel = viewModel,
                onDismiss = { showDialog = false }
            )
        }
    }
}


@Composable
fun TrackList(tracks: List<Track>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .testTag("TrackList")
    ) {
        tracks.forEach { track ->
            TrackItem(trackName = track.name, duration = track.duration)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TrackItem(trackName: String, duration: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp)
            .testTag("TrackItem")
            .background(Color.Transparent, RoundedCornerShape(8.dp))
            .border(
                1.dp, color = Color.Black, RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Album,
            contentDescription = "Icono de track: $trackName"
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(
                text = trackName,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold
            )
            Text(text = duration)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTrackDialog(
    albumId: Int,
    viewModel: AlbumViewModel,
    onDismiss: () -> Unit
) {
    var trackName by remember { mutableStateOf("") }
    var trackDuration by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = {
            errorMessage = ""
            onDismiss()
        },
        title = {
            Text(
                text = "Agregar Track",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Column {
                TextField(
                    value = trackName,
                    onValueChange = {
                        trackName = it
                        errorMessage = ""
                    },
                    label = { Text("Nombre del Track") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = trackDuration,
                    onValueChange = {
                        trackDuration = it
                        errorMessage = ""
                    },
                    label = { Text("Duración (HH:MM)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (trackName.isBlank()) {
                        errorMessage = "El nombre del track no puede estar vacío."
                        return@Button
                    }
                    if (!trackDuration.matches(Regex("^\\d{2}:\\d{2}\$"))) {
                        errorMessage = "La duración debe estar en el formato HH:MM."
                        return@Button
                    }

                    viewModel.addTrackToAlbum(
                        context = context,
                        albumID = albumId,
                        trackName = trackName,
                        trackDuration = trackDuration
                    )
                    errorMessage = ""
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    errorMessage = ""
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text("Cancelar")
            }
        }
    )
}
