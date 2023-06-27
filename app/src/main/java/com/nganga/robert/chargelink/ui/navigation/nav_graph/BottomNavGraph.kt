package com.nganga.robert.chargelink.ui.navigation.nav_graph

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.nganga.robert.chargelink.ui.navigation.BottomBarScreen
import com.nganga.robert.chargelink.ui.navigation.BOTTOM_NAV_ROUTE
import com.nganga.robert.chargelink.screens.bottom_nav_screens.*
import com.nganga.robert.chargelink.ui.viewmodels.HomeScreenViewModel

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
            MapScreen(viewModel = it.sharedViewModel(navController))
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
                viewModel = entry.sharedViewModel(navController)
            )
        }
    }
}
