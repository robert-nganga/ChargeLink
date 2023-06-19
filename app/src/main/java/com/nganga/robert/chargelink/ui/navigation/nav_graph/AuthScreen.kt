package com.nganga.robert.chargelink.ui.navigation.nav_graph


const val AUTHENTICATION_ROUTE = "authentication"
sealed class AuthScreen(val route: String) {
    object EnterNumber: AuthScreen(route = "enterNumber")
    object OtpVerification: AuthScreen(route = "otpVerification")
    object Register: AuthScreen(route = "register")
    object CarDetails: AuthScreen(route = "carDetails")
}