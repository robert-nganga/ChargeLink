package com.nganga.robert.chargelink.ui.navigation.nav_graph

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nganga.robert.chargelink.ui.navigation.MAIN_ROUTE
import com.nganga.robert.chargelink.ui.screens.authentication.EnterCarDetailsScreen
import com.nganga.robert.chargelink.ui.screens.authentication.LoginScreen
import com.nganga.robert.chargelink.ui.screens.authentication.RegisterUserScreen
import com.nganga.robert.chargelink.ui.screens.authentication.SignUpScreen


fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = AuthScreen.Login.route,
        route = AUTHENTICATION_ROUTE
    ){
        composable(route = AuthScreen.Login.route){
            LoginScreen(
                onSubmitClicked = {
                    navController.navigate(route = AuthScreen.SingUp.route)
                },
                viewModel = it.sharedViewModel(navController)
            )
        }
        composable(route = AuthScreen.SingUp.route){
            SignUpScreen(
                onSignUpClicked = {
                    navController.navigate(route = AuthScreen.Register.route)
                },
                viewModel = it.sharedViewModel(navController)
            )
        }
        composable(route = AuthScreen.Register.route){
            RegisterUserScreen(
                viewModel = it.sharedViewModel(navController)
            ) {
                navController.navigate(route = AuthScreen.CarDetails.route)
            }
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

@SuppressLint("RememberReturnType")
@Composable
inline fun <reified T: ViewModel>NavBackStackEntry.sharedViewModel(navController: NavHostController): T {
    val navRoute = destination.parent?.route ?: hiltViewModel()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navRoute)
    }
    return hiltViewModel(parentEntry)
}