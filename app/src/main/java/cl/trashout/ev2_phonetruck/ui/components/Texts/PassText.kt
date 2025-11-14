package cl.trashout.ev2_phonetruck.ui.components.Texts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun  PassText (

    password: String,
    onPasswordChange:(String) -> Unit
    ){
        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
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

