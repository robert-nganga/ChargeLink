package com.nganga.robert.chargelink.ui.navigation.nav_graph

import android.app.Activity
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.nganga.robert.chargelink.screens.bottom_nav_screens.*
import com.nganga.robert.chargelink.screens.bottom_nav_screens.map_screen.MapScreen
import com.nganga.robert.chargelink.screens.bottom_nav_screens.profile_screens.ProfileScreen
import com.nganga.robert.chargelink.ui.navigation.*

fun NavGraphBuilder.bottomNavGraph(
    navController: NavHostController,
    activity: Activity
) {
    navigation(
        startDestination = BottomBarScreen.Home.route,
        route = BOTTOM_NAV_ROUTE
    ){
        composable(route = BottomBarScreen.Home.route){
            HomeScreen(
                viewModel = it.sharedViewModel(navController),
                onNearByItemClick = { id ->
                    navController.navigate(BottomBarScreen.Details.withArgs(id))
                },
                activity = activity
            )
        }
        composable(route = BottomBarScreen.Maps.route){
            MapScreen(
                homeScreenViewModel = it.sharedViewModel(navController),
                mapScreenViewModel = it.sharedViewModel(navController),
                onStationClicked = { id ->
                    navController.navigate(BottomBarScreen.Details.withArgs(id)){
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = BottomBarScreen.Profile.route){
            ProfileScreen(
                onSettingsClick = {
                    navController.navigate(BottomBarScreen.Settings.route){
                        //popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                profileViewModel = it.sharedViewModel(navController),
                onLogOut = {
                    navController.navigate(AUTHENTICATION_ROUTE){
                        popUpTo(BOOKING_ROUTE){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = BottomBarScreen.Bookings.route){
            BookingsScreen(
                onBackPressed = { navController.popBackStack() },
                viewModel = it.sharedViewModel(navController),
                onBookingClicked = { bookingId ->
                    navController.navigate(BookingScreen.BookingConfirmation.withArgs(bookingId)){
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = BottomBarScreen.Settings.route){
            SettingsScreen()
        }
        composable(
            route = BottomBarScreen.Details.route + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                    nullable =  true
                }
            )
        ){ entry ->
            StationDetailsScreen(
                id = entry.arguments?.getString("id"),
                viewModel = entry.sharedViewModel(navController),
                onBookClicked = {stationId->
                    navController.navigate("$BOOKING_ROUTE/$stationId"){
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
