package co.edu.uniandes.vinilos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.uniandes.vinilos.model.models.Album
import co.edu.uniandes.vinilos.model.providers.AlbumProviderAPI
import co.edu.uniandes.vinilos.model.repository.AlbumRepository
import co.edu.uniandes.vinilos.ui.components.MainScreenWithBottomBar
import co.edu.uniandes.vinilos.ui.theme.ErrorRed
import co.edu.uniandes.vinilos.viewmodel.AlbumViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun AddAlbumnScreen(
    navController: NavController,
    viewModel: AlbumViewModel =
        AlbumViewModel(AlbumRepository(AlbumProviderAPI(LocalContext.current)))) {

    val scrollState = rememberScrollState()

    var name by remember { mutableStateOf("") }
    var cover by remember { mutableStateOf("") }
    var releaseDate by remember { mutableStateOf("") } 
    var description by remember { mutableStateOf("") }
    val genreOptions = listOf("Classical", "Salsa", "Rock", "Folk")
    var genre by remember { mutableStateOf(genreOptions.firstOrNull() ?: "") }
    val recordOptions = listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")
    var recordLabel by remember { mutableStateOf(recordOptions.firstOrNull() ?: "") }

    var hasNameFieldError by remember { mutableStateOf(false) }
    var hasCoverFieldError by remember { mutableStateOf(false) }
    var hasReleaseDateFieldError by remember { mutableStateOf(false) }
    var hasDescriptionFieldError by remember { mutableStateOf(false) }
    var isFormValid  by remember { mutableStateOf(false) }

    val textFieldColors = TextFieldDefaults.colors(
        errorIndicatorColor = ErrorRed,
        errorTextColor= ErrorRed,
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent
    )

    fun validateField(field: String): Boolean {
        return field.isNotBlank() && field.length > 3
    }

    fun isDateValid(date: String, format: String = "yyyy-MM-dd"): Boolean {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        dateFormat.isLenient = false

        return try {
            dateFormat.parse(date) != null
        } catch (e: Exception) {
            false
        }
    }

    fun isFormValid(): Boolean {
        return validateField(name) &&  validateField(cover) && isDateValid(releaseDate) &&
                validateField(description) &&  validateField(genre) && validateField(recordLabel)
    }

    MainScreenWithBottomBar(
        navController = navController,
        initialTitle = "",
        onOptionSelected = { route ->
            navController.navigate(route)
        },
        showBackIcon = true,
        backDestination =  "get_albumes/Coleccionista/0",
        profile = "Coleccionista"
    ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Vinilos") },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            ) { paddingValues -> // Padding para el contenido principal
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(paddingValues) // Aplica padding del Scaffold
                        .padding(horizontal = 16.dp)
                    .padding(bottom =100.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Agregar un nuevo álbum",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            hasNameFieldError = !validateField(field = name)
                            isFormValid = isFormValid()
                        },
                        label = { Text("Nombre") },
                        placeholder = { Text("Ej: Pablo Perez") },
                        isError = hasNameFieldError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("NombreField"),
                        colors = textFieldColors,
                        trailingIcon = {
                            if(hasNameFieldError){
                                Icon(
                                    imageVector = Icons.Filled.Error,
                                    tint = ErrorRed,
                                    contentDescription = "Select date"
                                )
                            }
                        }

                    )
                    if (hasNameFieldError) {
                        ErrorTextInfo(
                            text = "El nombre del álbum es obligatorio"
                        )
                    }

                    OutlinedTextField(
                        value = cover,
                        onValueChange = {
                            cover = it
                            hasCoverFieldError = !validateField(field = cover)
                            isFormValid = isFormValid()
                        },
                        label = { Text("Portada") },
                        placeholder = { Text("Ej: https://i.pinimg.com/image.jpg") },
                        isError =  hasCoverFieldError,
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("PortadaField"),
                        colors = textFieldColors,
                        trailingIcon = {
                            if(hasCoverFieldError){
                                Icon(
                                    imageVector = Icons.Filled.Error,
                                    tint = ErrorRed,
                                    contentDescription = "Select date"
                                )
                            }
                        }
                    )
                    if (hasCoverFieldError) {
                        ErrorTextInfo(
                            text = "La portada del albúm es obligatoria"
                        )
                    }

                DatePickerDocked(
                    onDateSelected = { selectedDate ->
                        releaseDate = selectedDate // La fecha ya está en el formato yyyy-MM-dd
                        hasReleaseDateFieldError = !isDateValid(releaseDate)
                        isFormValid = isFormValid()
                    },
                    onDismiss = { /* Acción al cerrar el DatePicker sin seleccionar */ }
                )

                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            description = it
                            hasDescriptionFieldError = !validateField(description)
                            isFormValid = isFormValid()
                        },
                        label = { Text("Descripción") },
                        isError =  hasDescriptionFieldError,
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("DescripciónField"),
                        maxLines = 5, // Define el límite de líneas (o elimina para ilimitado)
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Default
                        ),
                        colors = textFieldColors
                    )
                    CustomDropDown(
                        text = "Género",
                        options = genreOptions,
                        onSelected = {
                                value ->
                            if (value != null) {
                                genre = value
                            }
                        },
                        modifier = Modifier.testTag("GeneroField") ,
                        selectedValue = genre,

                    )


                    CustomDropDown(
                        text = "Sello discográfico",
                        options = recordOptions,
                        onSelected = {
                                value ->
                            if (value != null) {
                                recordLabel = value
                            }
                        },
                        modifier = Modifier.testTag("SelleDiscograficoField"),
                        selectedValue = recordLabel,

                    )
                    if(!isFormValid) {
                        ErrorBackgroundText("Todos los campos son obligatorios")
                    }

                    // Botón para agregar el nuevo elemento a la lista

                    Button(
                        onClick = {
                            val newAlbum = Album(
                                name = name,
                                cover = cover,
                                releaseDate = releaseDate,
                                description = description,
                                genre = genre,
                                recordLabel = recordLabel,
                            )
                            viewModel.addAlbum(newAlbum, navController)
                        },
                        enabled = isFormValid(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Agregar", fontSize = 18.sp)
                    }

                }
            }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    onDateSelected: (String) -> Unit, 
    onDismiss: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf("") }

    val textFieldColors  = TextFieldDefaults.colors(
        errorIndicatorColor = ErrorRed,
        errorTextColor= ErrorRed,
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent
    )

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = {
                            },
            label = { Text("Fecha de lanzamiento") },
            placeholder = { Text("dd/mm/yyyy") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .testTag("FechaField"),
            colors = textFieldColors
        )

        if (showDatePicker) {


            DatePickerDialog(
                onDismissRequest = {
                    onDismiss()
                    showDatePicker = false
                                   },
                confirmButton = {
                    TextButton(onClick = {
                        val selectedMillis = datePickerState.selectedDateMillis
                        if (selectedMillis != null) {
                            val date = Date(selectedMillis)
                            selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date) // Para mostrar
                            onDateSelected(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)) // Para enviar
                        }
                        showDatePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Composable
fun ErrorTextInfo(text: String) {
    Text(
        text = text,
        color = ErrorRed,
        modifier = Modifier.padding(start = 12.dp)
    )
}

@Composable
fun ErrorBackgroundText(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(140.dp)
            .padding(horizontal = 16.dp)
            .background(ErrorRed,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(25.dp),
        contentAlignment = Alignment.Center

    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = Color.White
        )

    }
}

@Composable
fun CustomDropDown(
    text: String,
    options: List<String>,
    onSelected: (String?) -> Unit,
    modifier: Modifier,
    selectedValue: String
){
    var isMenuExpanded by remember { mutableStateOf(false) }
    var currentSelection by remember { mutableStateOf(selectedValue) }
    OutlinedTextField(
        value = currentSelection,
        onValueChange = { selectedOption -> currentSelection = selectedOption },
        readOnly = true,
        label = { Text(text) },
        trailingIcon = {
            Icon(
                imageVector = if (isMenuExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = "Desplegar menú",
                modifier = Modifier.clickable { isMenuExpanded = !isMenuExpanded }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isMenuExpanded = !isMenuExpanded } // Abre el menú al hacer clic
    )

    // Menú desplegable
    DropdownMenu(
        expanded = isMenuExpanded,
        onDismissRequest = { isMenuExpanded = false }
    ) {
        options.forEach { option ->
            DropdownMenuItem(
                text = { Text(option) },
                onClick = {
                    currentSelection = option
                    onSelected(option)
                    isMenuExpanded = false // Cierra el menú
                }
            )
        }
    }
}
