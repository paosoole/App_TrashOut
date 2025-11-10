package cl.trashout.ev2_phonetruck.ui.components.Buttoms

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ButtonLogin(
    onClick: ( ) -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,  // ✅ Usar el parámetro onClick
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF00BCD4)
        )
    ) {
        Text("Ingresar", color = Color.White)
    }
}
