package cl.trashout.ev2_phonetruck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels     // <--- IMPORTANTE
import cl.trashout.ev2_phonetruck.viewModel.MainViewModel
import cl.trashout.ev2_phonetruck.ui.navigation.AppNavigation
import cl.trashout.ev2_phonetruck.ui.theme.EV2_PhoneTruckTheme

class MainActivity : ComponentActivity() {

    // ⭐ CREAR AQUÍ EL MAINVIEWMODEL COMPARTIDO PARA TODA LA APP
    private val mainVm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EV2_PhoneTruckTheme {

                // Pasar mainVm al NavHost
                AppNavigation(mainVm)
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    EV2_PhoneTruckTheme {
//        Greeting("Android")
//    }}}