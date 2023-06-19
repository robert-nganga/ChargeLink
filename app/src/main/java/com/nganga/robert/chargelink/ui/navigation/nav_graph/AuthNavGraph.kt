package com.nganga.robert.chargelink

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nganga.robert.chargelink.ui.navigation.MAIN_ROUTE
import com.nganga.robert.chargelink.ui.navigation.nav_graph.AUTHENTICATION_ROUTE
import com.nganga.robert.chargelink.ui.navigation.nav_graph.AuthScreen
import com.nganga.robert.chargelink.ui.screens.authentication.EnterCarDetailsScreen
import com.nganga.robert.chargelink.ui.screens.authentication.EnterNumberScreen
import com.nganga.robert.chargelink.ui.screens.authentication.OtpVerificationScreen
import com.nganga.robert.chargelink.ui.screens.authentication.RegisterUserScreen
import com.nganga.robert.chargelink.ui.viewmodels.AuthenticationViewModel


fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = AuthScreen.EnterNumber.route,
        route = AUTHENTICATION_ROUTE
    ){
        composable(route = AuthScreen.EnterNumber.route){
            EnterNumberScreen(
                onContinueClicked = {
                    navController.navigate(route = AuthScreen.OtpVerification.route)
                }
            )
        }
        composable(route = AuthScreen.OtpVerification.route){
            OtpVerificationScreen(
                onContinueClicked = {
                    navController.navigate(route = AuthScreen.Register.route)
                }
            )
        }
        composable(route = AuthScreen.Register.route){
            RegisterUserScreen(
                viewModel = AuthenticationViewModel(),
                onContinueClicked = {
                    navController.navigate(route = AuthScreen.CarDetails.route)
                }
            )
        }
        composable(route = AuthScreen.CarDetails.route){
            EnterCarDetailsScreen(
                onFinishClicked = {
                    navController.navigate(route = MAIN_ROUTE)
                }
            )
        }
    }
}