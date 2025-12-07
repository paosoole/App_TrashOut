package cl.trashout.ev2_phonetruck.ui.screens

import RegistroViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.trashout.ev2_phonetruck.TrashOut
import cl.trashout.ev2_phonetruck.ui.components.buttoms.BackButton
import cl.trashout.ev2_phonetruck.ui.components.validaciones.*
import cl.trashout.ev2_phonetruck.ui.navigation.AppScreens
import cl.trashout.ev2_phonetruck.viewModel.RegistroViewModelFactory
import cl.trashout.ev2_phonetruck.ui.components.barras.TopBar
import cl.trashout.ev2_phonetruck.ui.components.validaciones.ValidacionEmail
import cl.trashout.ev2_phonetruck.ui.components.validaciones.ValidacionPassword
import cl.trashout.ev2_phonetruck.ui.components.validaciones.ValidacionText
import cl.trashout.ev2_phonetruck.utils.ValidacionUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(navController: NavController) {

    // Repositorio y ViewModel
    val userRepository = TrashOut.userRepository
    val viewModel: RegistroViewModel = viewModel(
        factory = RegistroViewModelFactory(userRepository)
    )

    // Estados del formulario
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var comuna by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    var mensaje by remember { mutableStateOf("") }
    var registroExitoso by remember { mutableStateOf<Boolean?>(null) }

    val comunas = listOf(
        "Cerrillos","Cerro Navia","Conchalí","El Bosque","Estación Central","Huechuraba",
        "Independencia","La Cisterna","La Florida","La Granja","La Pintana","La Reina",
        "Las Condes","Lo Barnechea","Lo Espejo","Lo Prado","Macul","Maipú","Ñuñoa",
        "Pedro Aguirre Cerda","Peñalolén","Providencia","Pudahuel","Quilicura",
        "Quinta Normal","Recoleta","Renca","San Joaquín","San Miguel","San Ramón",
        "Santiago","Vitacura","Puente Alto","Pirque","San José de Maipo","Colina",
        "Lampa","Tiltil","San Bernardo","Buin","Paine","Calera de Tango","Melipilla",
        "Alhué","Curacaví","María Pinto","San Pedro","Talagante","El Monte",
        "Isla de Maipo","Padre Hurtado","Peñaflor"
    )

    // Validaciones
    val isNombreValid = ValidacionUtils.isNotEmpty(nombre)
    val isCorreoValid = ValidacionUtils.isValidEmail(correo)
    val isUsernameValid = ValidacionUtils.isNotEmpty(username)
    val isPasswordValid = ValidacionUtils.isValidPassword(password)
    val isConfirmPassValid = ValidacionUtils.isMatchingPasswords(password, confirmPassword)
    val isComunaValid = comuna.isNotEmpty()

    val formularioValido =
        isNombreValid &&
                isCorreoValid &&
                isUsernameValid &&
                isPasswordValid &&
                isConfirmPassValid &&
                isComunaValid


    Scaffold(
        topBar = {TopBar() },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF00BCD4),
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
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            BackButton(navController = navController)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Formulario de Registro",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF00BCD4),
                textAlign = TextAlign.Center
            )

            // Nombre
            ValidacionText(
                value = nombre,
                onChange = { nombre = it },
                label = "Nombre completo",
                isValid = isNombreValid,
                errorMessage = "El nombre no puede estar vacío"
            )

            // Correo
            ValidacionEmail(
                email = correo,
                onEmailChange = { correo = it }
            )

            // Username
            ValidacionText(
                value = username,
                onChange = { username = it },
                label = "Nombre de usuario",
                isValid = isUsernameValid,
                errorMessage = "Debe ingresar un nombre de usuario"
            )

            // Contraseña
            ValidacionPassword(
                password = password,
                onPasswordChange = { password = it }
            )

            // Confirmar contraseña
            ValidacionPassConfirm(
                password = password,
                confirmPassword = confirmPassword,
                onConfirmChange = { confirmPassword = it }
            )

            // Comuna
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
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
                    },
                    isError = !isComunaValid && comuna.isNotEmpty()
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
                    viewModel.registrar(
                        nombre = nombre,
                        correo = correo,
                        username = username,
                        password = password,
                        comuna = comuna
                    ) { success, error ->

                        if (success) {
                            registroExitoso = true
                            mensaje = "Usuario Registrado Exitosamente"

                            navController.navigate(AppScreens.LoginScreen.route) {
                                popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                            }

                        } else {
                            registroExitoso = false
                            mensaje = error ?: "Error al registrar usuario"
                        }
                    }
                },
                enabled = formularioValido,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4),
                    disabledContainerColor = Color.LightGray
                )
            ) {
                Text("Registrar")
            }


            // Mensaje de éxito o error
            if (mensaje.isNotEmpty()) {
                Text(
                    text = mensaje,
                    color = if (registroExitoso == true) Color(0xFF4CAF50) else Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }


        }
    }
}
@Preview(showBackground = true)
@Composable
fun RegistroScreenPreview() {
    val fakeNavController = androidx.navigation.compose.rememberNavController()
    RegistroScreen(navController = fakeNavController)
}
