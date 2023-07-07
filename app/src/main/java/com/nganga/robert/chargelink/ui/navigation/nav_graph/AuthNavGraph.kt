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
import com.nganga.robert.chargelink.ui.navigation.BOTTOM_NAV_ROUTE
import com.nganga.robert.chargelink.screens.authentication_screens.EnterCarDetailsScreen
import com.nganga.robert.chargelink.screens.authentication_screens.LoginScreen
import com.nganga.robert.chargelink.screens.authentication_screens.RegisterUserScreen
import com.nganga.robert.chargelink.screens.authentication_screens.SignUpScreen
import com.nganga.robert.chargelink.ui.navigation.AUTHENTICATION_ROUTE
import com.nganga.robert.chargelink.ui.navigation.AuthScreen


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
                    navController.navigate(route = AuthScreen.SingUp.route){
                        launchSingleTop = true
                    }
                },
                viewModel = it.sharedViewModel(navController),
                onNavigateToSignUp = {
                    navController.navigate(route = AuthScreen.SingUp.route){
                        launchSingleTop = true
                    }
                },
                onLoginSuccessful = {
                    navController.navigate(route = BOTTOM_NAV_ROUTE){
                        launchSingleTop = true
                        popUpTo(AUTHENTICATION_ROUTE){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = AuthScreen.SingUp.route){
            SignUpScreen(
                onSignUpClicked = {
                    navController.navigate(route = AuthScreen.Register.route)
                },
                onNavigateToLogin = {
                    navController.navigate(route = AuthScreen.Login.route) {
                        launchSingleTop = true
                    }
                },
                viewModel = it.sharedViewModel(navController)

            )
        }
        composable(route = AuthScreen.Register.route){
            RegisterUserScreen(
                viewModel = it.sharedViewModel(navController),
                onContinueClicked = {
                    navController.navigate(route = AuthScreen.CarDetails.route){
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = AuthScreen.CarDetails.route){
            EnterCarDetailsScreen(
                onFinishClicked = {
                    navController.navigate(route = BOTTOM_NAV_ROUTE){
                        launchSingleTop = true
                        popUpTo(AUTHENTICATION_ROUTE){
                            inclusive = true
                        }
                    }
                },
                viewModel = it.sharedViewModel(navController)
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