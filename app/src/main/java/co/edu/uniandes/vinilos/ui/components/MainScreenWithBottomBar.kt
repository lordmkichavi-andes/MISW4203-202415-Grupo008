package co.edu.uniandes.vinilos.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontWeight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenWithBottomBar(
    navController: NavController,
    initialTitle: String,
    onOptionSelected: (String) -> Unit,
    showBackIcon: Boolean = false,
    backDestination: String,
    profile: String,
    content: @Composable (PaddingValues) -> Unit,
) {
    var title by remember { mutableStateOf(initialTitle) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Catálogo de álbumes") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title, fontWeight = FontWeight.Bold ) },
                navigationIcon = {
                    Box {
                        if (showBackIcon) {
                            IconButton(onClick = { navController.navigate(backDestination) }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        } else {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú")
                            }
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .width(IntrinsicSize.Max) // Asegura que el menú tenga el ancho máximo necesario para el contenido
                                .offset(x = (-16).dp, y = 8.dp) // Ajusta la posición
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Catálogo de álbumes",
                                        fontWeight = FontWeight.Bold, // Texto en negrita
                                        color = if (selectedOption == "Catálogo de álbumes") MaterialTheme.colorScheme.primary else Color.Unspecified,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                },
                                onClick = {
                                    title = "Catálogo de álbumes"
                                    selectedOption = "Catálogo de álbumes"
                                    expanded = false
                                    onOptionSelected("get_albumes/$profile/0")
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Listado de artistas",
                                        fontWeight = FontWeight.Bold, // Texto en negrita
                                        color = if (selectedOption == "Listado de artistas") MaterialTheme.colorScheme.primary else Color.Unspecified,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                },
                                onClick = {
                                    title = "Listado de artistas"
                                    selectedOption = "Listado de artistas"
                                    expanded = false
                                    onOptionSelected("get_artistas")
                                }
                            )/*
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Listado de coleccionistas",
                                        fontWeight = FontWeight.Bold, // Texto en negrita
                                        color = if (selectedOption == "Listado de coleccionistas") MaterialTheme.colorScheme.primary else Color.Unspecified,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                },
                                onClick = {
                                    title = "Listado de coleccionistas"
                                    selectedOption = "Listado de coleccionistas"
                                    expanded = false
                                    onOptionSelected("get_coleccionistas")
                                }
                            )*/
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("login") }) { // Redirige al login al hacer clic
                        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Salir", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White
            ) {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        navController.navigate("get_albumes/$profile/0")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Ir al inicio",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        },
        content = content
    )
}
