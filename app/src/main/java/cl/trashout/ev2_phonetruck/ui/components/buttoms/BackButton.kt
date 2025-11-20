package cl.trashout.ev2_phonetruck.ui.components.buttoms


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BackButton(
    navController: NavController
) {
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Atrás",
            tint = Color(0xFF00BCD4) // o el color que necesites
        )
    }
}