package cl.trashout.ev2_phonetruck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import cl.trashout.ev2_phonetruck.ui.navigation.AppNavigation
import cl.trashout.ev2_phonetruck.ui.theme.EV2_PhoneTruckTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EV2_PhoneTruckTheme {
                AppNavigation()

            }
        }
    }
}
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Inicio $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    EV2_PhoneTruckTheme {
//        Greeting("Android")
//    }}}