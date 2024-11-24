package co.edu.uniandes.vinilos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.Album
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import co.edu.uniandes.vinilos.model.models.Collector
import co.edu.uniandes.vinilos.model.providers.CollectorProviderAPI
import co.edu.uniandes.vinilos.model.repository.CollectorRepository
import co.edu.uniandes.vinilos.ui.components.MainScreenWithBottomBar
import co.edu.uniandes.vinilos.viewmodel.Artist
import co.edu.uniandes.vinilos.viewmodel.CollectorViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CollectorsListScreen(
    navController: NavController
)
{
    val context = LocalContext.current
    val collectorRepository = remember { CollectorRepository(CollectorProviderAPI(context)) }
    val viewModel = remember { CollectorViewModel(collectorRepository) }

    val collectors by viewModel.collectors.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    MainScreenWithBottomBar(
        navController = navController,
        initialTitle = "Listado de coleccionistas",
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
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Cargando tus artistas de vinilos favoritos")
                    }
                }
                errorMessage != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = errorMessage ?: "Error desconocido")
                        Button(onClick = { viewModel.loadCollectors() }) {
                            Text("Reintentar")
                        }
                    }
                }
                collectors.isEmpty() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("¡Ups! No se encontraron artistas...")
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(top = 16.dp)
                    ) {
                        items(collectors) { collector ->
                            CollectorItem(collector = collector) {
                                navController.currentBackStackEntry?.savedStateHandle?.set("collector", collector)
                                navController.navigate("collector_detail")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CollectorItem(collector: Collector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("CollectorItem")
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Album,
                contentDescription = "Icono de un disco"
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = collector.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

            }
        }
    }
}