package com.nganga.robert.chargelink.ui.navigation.nav_graph

import android.app.Activity
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.nganga.robert.chargelink.ui.navigation.BottomBarScreen
import com.nganga.robert.chargelink.ui.navigation.BOTTOM_NAV_ROUTE
import com.nganga.robert.chargelink.screens.bottom_nav_screens.*
import com.nganga.robert.chargelink.screens.bottom_nav_screens.map_screen.MapScreen
import com.nganga.robert.chargelink.ui.navigation.BOOKING_ROUTE

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
                mapScreenViewModel = it.sharedViewModel(navController)
            )
        }
        composable(route = BottomBarScreen.Profile.route){
            ProfileScreen(
                onSettingsClick = {
                    navController.navigate(BottomBarScreen.Settings.route){
                        //popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = BottomBarScreen.Bookings.route){
            BookingsScreen(
                onBackPressed = { navController.popBackStack() },
                viewModel = it.sharedViewModel(navController)
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
                onBookClicked = {chargerId, stationId->
                    navController.navigate("$BOOKING_ROUTE/$stationId"){
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
