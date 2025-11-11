package cl.trashout.ev2_phonetruck.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import cl.trashout.ev2_phonetruck.R
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.trashout.ev2_phonetruck.ui.navigation.AppScreens
import android.content.res.Configuration
import cl.trashout.ev2_phonetruck.ui.components.Buttoms.ButtonLogin
@Composable
fun LoginScreen (navController: NavController ) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf( "")}

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
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LogoTrashOut()
            Spacer(modifier = Modifier.height(16.dp))
            MyTexts(Modifier.align(Alignment.CenterHorizontally))

            // Login
            LoginTextField(
                username = username,
                onUsernameChange = {username = it},
                password = password,
                onPasswordChange = {password = it}
            )


            Spacer(modifier = Modifier.height(16.dp))
            BoxOpciones(
                onRegistrarClick = {
                    // acción registrar
                    navController.navigate(AppScreens.RegistroScreen.route) {
                        popUpTo(AppScreens.RegistroScreen.route) { inclusive = true }
                    }
                },
                onOlvidoClick = {
                    // acción olvidar contraseña

                    // Aquí va la navegación
                    navController.navigate(AppScreens.ResetPassScreen.route) {
                        popUpTo(AppScreens.ResetPassScreen.route) { inclusive = true }
                    }
                }
            )

            // Botón de login
            ButtonLogin(
                onClick = {
                    // Aquí va la navegación
                    navController.navigate(AppScreens.TrackingScreen.route) {
                        popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTopBar(){
    TopAppBar(
        title = {Text ( "TrashOut")},
        navigationIcon = {
            IconButton(onClick = {/* manejar navegacion*/}) {
                Image(
                    painter = painterResource(id = R.drawable.camion),
                    contentDescription = "logo de la App",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(40.dp)
                )
            }
            },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF00BCD4), //modificar
            titleContentColor = Color.DarkGray,
            navigationIconContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun MyText(text : String){
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = Color(0xFF00BCD4),
        textAlign = TextAlign.Center,

    )
}

@Composable
fun MyTexts( modifier: Modifier = Modifier){
    Column (
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        MyText("Acceso para Conductores")
    }
}

@Composable
fun LogoTrashOut()
{
    Image(
        modifier = Modifier.fillMaxWidth(),
        painter = painterResource(id = R.drawable.logo_fondo),
        contentDescription = "logo de Fondo",
    )
}

@Composable
fun LoginTextField(
    username : String,
    onUsernameChange : (String) -> Unit,
    password: String,
    onPasswordChange:(String) -> Unit
){
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        //Campo Usuario
        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = {Text ("Usuario")},
            placeholder = {Text("Nombre de Usuario")},
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = "Usuario")
            },
            modifier = Modifier.fillMaxWidth()
        )

        //Campo Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = {Text("Contraseña")},
            placeholder = {Text("Ingrese Contraseña")},
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Contraseña")
            },

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}



@Composable
fun TextButtonOlvidoPass(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text("¿Olvidaste Contraseña?")
    }
}

@Composable
fun TextButtonRegistrar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text("Registrarse")
    }
}

@Composable
fun BoxOpciones(
    onRegistrarClick: () -> Unit,
    onOlvidoClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        TextButtonRegistrar(
            modifier = Modifier,
            onClick = onRegistrarClick
        )

        TextButtonOlvidoPass(
            modifier = Modifier,
            onClick = onOlvidoClick
        )
    }
}


@Preview(showBackground = true)

@Composable
fun LoginScreenPreview() {
    val fakeNavController = androidx.navigation.compose.rememberNavController()

    LoginScreen(
        navController = fakeNavController
    )
}
