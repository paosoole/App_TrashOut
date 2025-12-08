package cl.trashout.ev2_phonetruck.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.trashout.ev2_phonetruck.ui.screens.LoginScreen
import cl.trashout.ev2_phonetruck.ui.screens.RegistroScreen
import cl.trashout.ev2_phonetruck.ui.screens.ResetPassScreen
import cl.trashout.ev2_phonetruck.ui.screens.TrackingScreen
import cl.trashout.ev2_phonetruck.viewModel.MainViewModel

@Composable
fun AppNavigation (mainVm: MainViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController = navController,
                mainVm = mainVm)
        }
        composable(route = AppScreens.TrackingScreen.route){
            TrackingScreen( navController = navController)
        }
        composable(route = AppScreens.ResetPassScreen.route){
            ResetPassScreen(navController = navController)
        }
        composable (route = AppScreens.RegistroScreen.route){
            RegistroScreen(navController = navController)
        }
    }
}