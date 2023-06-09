package com.nganga.robert.chargelink.ui.navigation.nav_graph

import androidx.compose.runtime.remember
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nganga.robert.chargelink.screens.booking_screens.BookingConfirmationScreen
import com.nganga.robert.chargelink.screens.booking_screens.EnterBookingDetailsScreen
import com.nganga.robert.chargelink.screens.booking_screens.PaymentDetailsScreen
import com.nganga.robert.chargelink.screens.booking_screens.SelectChargerScreen
import com.nganga.robert.chargelink.ui.navigation.BOOKING_ROUTE
import com.nganga.robert.chargelink.ui.navigation.BookingScreen
import com.nganga.robert.chargelink.ui.navigation.BottomBarScreen


fun NavGraphBuilder.bookingNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = BookingScreen.SelectCharger.route,
        route = "$BOOKING_ROUTE/{id}",
        arguments = listOf(
            navArgument("id") { type = NavType.StringType }
        )
    ){
        composable(
            route = BookingScreen.SelectCharger.route
        ){
            val parentEntry = remember(it) { navController.getBackStackEntry("$BOOKING_ROUTE/{id}") }
            val id = parentEntry.arguments?.getString("id")
            SelectChargerScreen(
                bookingViewModel = it.sharedViewModel(navController),
                stationId = id,
                onBackButtonClicked = {
                    navController.popBackStack()
                },
                onNextButtonClicked = {
                    navController.navigate(BookingScreen.EnterBookingDetails.route){
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = BookingScreen.EnterBookingDetails.route
        ){
            EnterBookingDetailsScreen(
                bookingViewModel = it.sharedViewModel(navController),
                onBackButtonClicked = {
                    navController.popBackStack()
                },
                onNextButtonClicked = {
                    navController.navigate(BookingScreen.PaymentDetails.route){
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = BookingScreen.PaymentDetails.route){
            PaymentDetailsScreen(
                onBackButtonClicked = {
                    navController.popBackStack()
                },
                onContinueClicked = {
                    navController.navigate(BookingScreen.BookingConfirmation.route+"/none"){
                        launchSingleTop = true
                    }
                },
                bookingViewModel = it.sharedViewModel(navController)
            )
        }

        composable(
            route = BookingScreen.BookingConfirmation.route+"/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                    nullable =  true
                }
            )
        ){
            BookingConfirmationScreen(
                onBackButtonClicked = {
                    navController.popBackStack()
                },
                onConfirmButtonClicked = {
                    (1..4).forEach { _ ->
                        navController.popBackStack()
                    }
                },
                bookingViewModel = it.sharedViewModel(navController),
                bookingId = it.arguments?.getString("id")
            )
        }
    }
}