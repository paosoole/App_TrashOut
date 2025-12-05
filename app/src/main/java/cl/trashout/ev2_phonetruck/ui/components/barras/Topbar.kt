package cl.trashout.ev2_phonetruck.ui.components.barras

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cl.trashout.ev2_phonetruck.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text("Trash Out") },
        navigationIcon = {
            IconButton(onClick = { /* manejar clic del ícono */ }) {
                Image(
                    painter = painterResource(id = R.drawable.camion),
                    contentDescription = "Logo de la app",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(40.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF00BCD4),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}
