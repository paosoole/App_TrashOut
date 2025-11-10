package cl.trashout.ev2_phonetruck.ui.navigation

sealed class AppScreens (val route: String){
    object LoginScreen : AppScreens(route = "login_screen")

    object TrackingScreen : AppScreens(route = "tracking_screen")

    object ResetPassScreen : AppScreens(route = "resetpass_screen")

    object RegistroScreen : AppScreens(route = "registro_screen")

}