package co.edu.uniandes.vinilos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.edu.uniandes.vinilos.model.providers.AlbumProviderAPI
import co.edu.uniandes.vinilos.model.repository.AlbumRepository
import co.edu.uniandes.vinilos.ui.components.MainScreenWithBottomBar
import co.edu.uniandes.vinilos.viewmodel.AlbumViewModel
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.sp
import co.edu.uniandes.vinilos.R
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.models.Track
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun GetAlbumScreen(
    navController: NavController,
    profile:String,
    albumID: String?,
    viewModel: AlbumViewModel = AlbumViewModel(AlbumRepository(AlbumProviderAPI(LocalContext.current)))
)
{
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    if (albumID != null) {
        viewModel.getAlbum(context, albumID.toInt())
    }
    val album by viewModel.album.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val targetFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = originalFormat.parse(album.releaseDate)
    date?.let { targetFormat.format(it) } ?: "Fecha desconocida"

    MainScreenWithBottomBar(
        navController = navController,
        initialTitle = "",
        onOptionSelected = { route ->
            navController.navigate(route)
        },
        showBackIcon = true,
        backDestination =  "get_albumes/$profile/0",
        profile = profile
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
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

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Color.White)
                    .verticalScroll(scrollState)
                    .padding(top=50.dp, bottom = 50.dp)
            ) { // Imagen
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
                        contentDescription = "Imagen de álbum",
                        modifier = Modifier.fillMaxWidth()
                    )

                }

                Spacer(modifier = Modifier.height(20.dp))

                // Título
                Text(text = album.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(20.dp))

                // Fecha y género
                Text(text = date?.let { targetFormat.format(it) } ?: "Fecha desconocida", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Row{
                    Icon(
                        imageVector = Icons.Filled.MusicNote,
                        contentDescription = "Nota Musical"
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = album.genre, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Descripción
                Text(
                    text =album.description,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Sello discográfico
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    Row( modifier = Modifier.align(Alignment.BottomEnd)) {
                        Icon(
                            imageVector = Icons.Filled.Album,
                            contentDescription = "Icono de un disco"
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = album.recordLabel,
                            fontSize = 18.sp,

                        )
                    }

                }


                Spacer(modifier = Modifier.height(20.dp))

                // Sección de Tracks

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = "Tracks",
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )

                }

                // Agregar track
                if(profile == "Coleccionista"){
                    AddTrackButton(navController)
                }

                // Tracks
                album.tracks?.let { TrackList(it) }

                Spacer(modifier = Modifier.weight(1f))


            }

        }
    }
}


@Composable
private fun AddTrackButton(navController: NavController) {
    Button(
        onClick = {  },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent
        ),
        modifier = Modifier
            .padding(8.dp)
            .testTag("AgregarTrackButton")
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
            text = "Agregar track",
            color = Color.Black
        )
    }
}

@Composable
fun TrackList(tracks: List<Track>){
    tracks.forEach {
        track ->
        TrackItem(trackName = track.name, duration = track.duration)
        Spacer(modifier = Modifier.height(8.dp))
    }
}


@Composable
fun TrackItem(trackName: String, duration: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp)
            .background(Color.Transparent, RoundedCornerShape(8.dp))
            .border(
                1.dp, color = Color.Black, RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Filled.Album, contentDescription = null)
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(text = trackName, modifier = Modifier.weight(1f))
            Text(text = duration)
        }
    }
}
