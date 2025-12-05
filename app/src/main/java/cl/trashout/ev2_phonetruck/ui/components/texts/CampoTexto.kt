package cl.trashout.ev2_phonetruck.ui.components.texts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CampoTexto(
        username : String,
        onUsernameChange : (String) -> Unit,
    ){
        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            //Campo Usuario
            OutlinedTextField(
                value = username,
                onValueChange = onUsernameChange,
                label = { Text("Usuario") },
                placeholder = { Text("Nombre de Usuario") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Usuario")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
}
