package com.nganga.robert.chargelink.ui.navigation.nav_graph


const val AUTHENTICATION_ROUTE = "authentication"
sealed class AuthScreen(val route: String) {
    object Login: AuthScreen(route = "login")
    object SingUp: AuthScreen(route = "signUp")
    object Register: AuthScreen(route = "register")
    object CarDetails: AuthScreen(route = "carDetails")
}