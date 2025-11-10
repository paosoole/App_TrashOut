package cl.trashout.ev2_phonetruck.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.trashout.ev2_phonetruck.domain.model.RegistroViewModel
import cl.trashout.ev2_phonetruck.domain.model.RegistroViewModelFactory
import cl.trashout.ev2_phonetruck.ui.components.Texts.InputText
import cl.trashout.ev2_phonetruck.TrashOut

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(navController: NavController) {

    val context = LocalContext.current
    val app = context.applicationContext as TrashOut   // 👈 Casteamos a tu clase Application

    // ✅ Accedemos correctamente al repositorio
    val userRepository = _root_ide_package_.cl.trashout.ev2_phonetruck.TrashOut.userRepository

    // ✅ Creamos el ViewModel con su factory
    val viewModel: RegistroViewModel = viewModel(
        factory = RegistroViewModelFactory(userRepository)
    )


    // Campos del formulario
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    // Lista de regiones de Chile
    val regiones = listOf(
        "Arica y Parinacota", "Tarapacá", "Antofagasta", "Atacama",
        "Coquimbo", "Valparaíso", "Metropolitana de Santiago",
        "Libertador General Bernardo O’Higgins", "Maule", "Ñuble",
        "Biobío", "La Araucanía", "Los Ríos", "Los Lagos",
        "Aysén del General Carlos Ibáñez del Campo",
        "Magallanes y de la Antártica Chilena"
    )

    Scaffold (
        topBar = {
            LoginTopBar()
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF00BCD4),
                contentColor = MaterialTheme.colorScheme.tertiary
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Servicio de Recoleccion",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Formulario de Registro",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            // Campo Nombre
            InputText(
                valor = nombre,
                error = null,
                label = "Nombre del Conductor",
                onChange = { nombre = it }
            )

            // Campo Correo
            InputText(
                valor = correo,
                error = null,
                label = "Correo electrónico",
                onChange = { correo = it }
            )

            // Región (Dropdown)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = region,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Región") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    regiones.forEach { regionSeleccionada ->
                        DropdownMenuItem(
                            text = { Text(regionSeleccionada) },
                            onClick = {
                                region = regionSeleccionada
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Botón Enviar
            Button(
                onClick = {
                    viewModel.registrar(nombre, correo, "1234") { success, error ->
                        mensaje = if (success)
                            "Usuario registrado correctamente"
                        else
                            error ?: "Error al registrar"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar")
            }

            if (mensaje.isNotEmpty()) {
                Text(mensaje, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroScreenPreview() {
    val fakeNavController_ = androidx.navigation.compose.rememberNavController()

    RegistroScreen(navController = fakeNavController_)
}
