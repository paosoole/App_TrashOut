package cl.trashout.ev2_phonetruck.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.trashout.ev2_phonetruck.TrashOut
import cl.trashout.ev2_phonetruck.ui.components.buttoms.BackButton
import cl.trashout.ev2_phonetruck.viewModel.ResetViewModel
import cl.trashout.ev2_phonetruck.ui.components.texts.CampoTexto
import cl.trashout.ev2_phonetruck.ui.navigation.AppScreens
import cl.trashout.ev2_phonetruck.viewModel.ResetViewModelFactory
import cl.trashout.ev2_phonetruck.ui.components.barras.TopBar
import cl.trashout.ev2_phonetruck.ui.components.barras.LogoTrashOut
import cl.trashout.ev2_phonetruck.ui.components.buttoms.ButtonReset
@Composable
fun ResetPassScreen(
    navController: NavController
) {

    // Base de datos + Repository

    val repository = TrashOut.userRepository

    // ViewModel con Factory
    val viewModel: ResetViewModel = viewModel(
        factory = ResetViewModelFactory(repository)
    )

    val estado by viewModel.estado.collectAsState()

    var username by remember { mutableStateOf("") }
    //var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {TopBar()
        },
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
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BackButton(navController = navController)
            Spacer(modifier = Modifier.height(20.dp))
            LogoTrashOut()
            Spacer(modifier = Modifier.height(16.dp))
            MyTexts(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(16.dp))
            CampoTexto(
                username = username,
                onUsernameChange = { username = it },
            )

            // --- RESULTADO DE LA VALIDACIÓN ---
            if (estado.loading) {
                Text("Validando usuario...", color = Color.Gray)
            }

            estado.error?.let {
                Text(it, color = Color.Red)
            }

            if (estado.emailDestino != null) {
                AlertDialog(
                    onDismissRequest = { },
                    title = {
                        Text("Correo encontrado")
                    },
                    text = {
                        Text("Se ha enviado un link de recuperacion de contraseña a: ${estado.emailDestino} ")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.limpiar()
                                navController.navigate(AppScreens.LoginScreen.route) {
                                    popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF24C1D9)
                            )
                        ) {
                            Text("Volver a Login")
                        }
                    }
                )
            }

            ButtonReset(
                onClick = {
                    viewModel.validarUsuario(username)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ResetPassScreenPreview() {
    val fakeNavController = androidx.navigation.compose.rememberNavController()
    ResetPassScreen(navController = fakeNavController)
}
