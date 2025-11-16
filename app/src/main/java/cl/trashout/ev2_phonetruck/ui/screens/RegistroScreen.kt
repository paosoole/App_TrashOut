package cl.trashout.ev2_phonetruck.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.trashout.ev2_phonetruck.viewModel.RegistroViewModel
import cl.trashout.ev2_phonetruck.viewModel.RegistroViewModelFactory
import cl.trashout.ev2_phonetruck.ui.components.Texts.InputText
import cl.trashout.ev2_phonetruck.TrashOut
import cl.trashout.ev2_phonetruck.ui.components.Buttoms.BackButton
import cl.trashout.ev2_phonetruck.ui.navigation.AppScreens
import cl.trashout.ev2_phonetruck.ui.components.Texts.PassText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(navController: NavController) {

    // Repositorio desde Application
    val userRepository = TrashOut.userRepository

    // ViewModel
    val viewModel: RegistroViewModel = viewModel(
        factory = RegistroViewModelFactory(userRepository)
    )

    // Campos del formulario
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var comuna by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    // Lista de regiones de Chile
    val comunas = listOf(
        "Cerrillos",
        "Cerro Navia",
        "Conchalí",
        "El Bosque",
        "Estación Central",
        "Huechuraba",
        "Independencia",
        "La Cisterna",
        "La Florida",
        "La Granja",
        "La Pintana",
        "La Reina",
        "Las Condes",
        "Lo Barnechea",
        "Lo Espejo",
        "Lo Prado",
        "Macul",
        "Maipú",
        "Ñuñoa",
        "Pedro Aguirre Cerda",
        "Peñalolén",
        "Providencia",
        "Pudahuel",
        "Quilicura",
        "Quinta Normal",
        "Recoleta",
        "Renca",
        "San Joaquín",
        "San Miguel",
        "San Ramón",
        "Santiago",
        "Vitacura",
        "Puente Alto",
        "Pirque",
        "San José de Maipo",
        "Colina",
        "Lampa",
        "Tiltil",
        "San Bernardo",
        "Buin",
        "Paine",
        "Calera de Tango",
        "Melipilla",
        "Alhué",
        "Curacaví",
        "María Pinto",
        "San Pedro",
        "Talagante",
        "El Monte",
        "Isla de Maipo",
        "Padre Hurtado",
        "Peñaflor"
    )

    Scaffold(
        topBar = { LoginTopBar() },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF00BCD4),
                contentColor = MaterialTheme.colorScheme.tertiary
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Servicio de Recolección",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BackButton(
                navController = navController)

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Formulario de Registro",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF00BCD4),
                textAlign = TextAlign.Center
            )

            // Nombre
            InputText(valor = nombre, error = null, label = "Nombre completo") {
                nombre = it
            }

            // Correo
            InputText(valor = correo, error = null, label = "Correo electrónico") {
                correo = it
            }

            // Username
            InputText(
                valor = username,
                error = null,
                label = "Nombre de usuario") {
                username = it
            }
            PassText(
                password = password,
                onPasswordChange = {password = it}
            )
            // Región / Comuna (Dropdown)
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = comuna,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Comuna") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                )

                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    comunas.forEach { comunaSeleccionada ->
                        DropdownMenuItem(
                            text = { Text(comunaSeleccionada) },
                            onClick = {
                                comuna = comunaSeleccionada
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Botón Registrar
            Button(
                onClick = {
                    val comunaSelec = comuna
                    viewModel.registrar(
                        nombre = nombre,
                        correo = correo,
                        username = username,
                        password = password,
                        comuna = comunaSelec
                    ) { success, error ->
                        if (success) {
                            mensaje = "Usuario registrado Exitosamente"
                            // Navegar a LoginScreen
                            navController.navigate(AppScreens.LoginScreen.route) {
                                popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                            }
                        } else {
                            mensaje = error ?: "Error al registrar"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4)
                )
            ) {
                Text("Registrar")
            }

            if (mensaje.isNotEmpty()) {
                Text(mensaje, color = Color(0xFF00BCD4))
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