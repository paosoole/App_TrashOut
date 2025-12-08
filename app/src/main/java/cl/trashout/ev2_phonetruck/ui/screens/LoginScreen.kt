package cl.trashout.ev2_phonetruck.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.trashout.ev2_phonetruck.ui.navigation.AppScreens
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.trashout.ev2_phonetruck.viewModel.LoginViewModel
import cl.trashout.ev2_phonetruck.ui.components.buttoms.ButtonLogin
import cl.trashout.ev2_phonetruck.viewModel.LoginViewModelFactory
import cl.trashout.ev2_phonetruck.TrashOut
import cl.trashout.ev2_phonetruck.ui.components.barras.TopBar
import cl.trashout.ev2_phonetruck.ui.components.barras.LogoTrashOut
import cl.trashout.ev2_phonetruck.viewModel.MainViewModel


@Composable
fun LoginScreen(navController: NavController, mainVm: MainViewModel) {

    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(TrashOut.userRepository)
    )
    val estado by viewModel.estado.collectAsState()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(48.dp),
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
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LogoTrashOut()
            Spacer(modifier = Modifier.height(16.dp))
            MyTexts(Modifier.align(Alignment.CenterHorizontally))

            LoginTextField(
                username = estado.username,
                onUsernameChange = { viewModel.onUsernameChange(it) },
                password = estado.password,
                onPasswordChange = { viewModel.onPasswordChange(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            BoxOpciones(
                onRegistrarClick = {
                    navController.navigate(AppScreens.RegistroScreen.route)
                },
                onOlvidoClick = {
                    navController.navigate(AppScreens.ResetPassScreen.route)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ButtonLogin(
                onClick = {
                    if (estado.username.isBlank() || estado.password.isBlank()) {
                        viewModel.setError("Debe ingresar usuario y contraseña")
                        return@ButtonLogin
                    }

                    viewModel.iniciarSesion {userId ->

                        mainVm.setUserId(userId)   // Guardar el id REAL del usuario

                        navController.navigate(AppScreens.TrackingScreen.route) {
                            popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            estado.error?.let { msg ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = msg,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun MyText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = Color(0xFF00BCD4),
        textAlign = TextAlign.Center,
    )
}

@Composable
fun MyTexts(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyText("Acceso para Conductores")
    }
}


@Composable
fun LoginTextField(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = username,                     // ✔ correcto
            onValueChange = onUsernameChange,     // ✔ correcto
            label = { Text("Usuario") },
            placeholder = { Text("Nombre de Usuario") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,                     // ✔ correcto
            onValueChange = onPasswordChange,     // ✔ correcto
            label = { Text("Contraseña") },
            placeholder = { Text("Ingrese Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TextButtonOlvidoPass(modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(onClick = onClick, modifier = modifier) { Text("¿Olvidaste Contraseña?") }
}

@Composable
fun TextButtonRegistrar(modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(onClick = onClick, modifier = modifier) { Text("Registrarse") }
}

@Composable
fun BoxOpciones(onRegistrarClick: () -> Unit, onOlvidoClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButtonRegistrar(onClick = onRegistrarClick)
        TextButtonOlvidoPass(onClick = onOlvidoClick)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    val fakeNavController = androidx.navigation.compose.rememberNavController()
//    LoginScreen(navController = fakeNavController)
//}
