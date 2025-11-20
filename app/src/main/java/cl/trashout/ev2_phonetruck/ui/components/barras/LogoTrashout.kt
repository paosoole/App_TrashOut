package cl.trashout.ev2_phonetruck.ui.components.barras

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import cl.trashout.ev2_phonetruck.R

@Composable
fun LogoTrashOut() {
    Image(
        modifier = Modifier.fillMaxWidth(),
        painter = painterResource(id = R.drawable.logo_fondo),
        contentDescription = "logo de Fondo",
    )
}