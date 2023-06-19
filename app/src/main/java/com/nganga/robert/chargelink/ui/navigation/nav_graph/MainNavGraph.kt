package com.nganga.robert.chargelink

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.nganga.robert.chargelink.ui.navigation.BottomBarScreen
import com.nganga.robert.chargelink.ui.navigation.MAIN_ROUTE
import com.nganga.robert.chargelink.ui.screens.*
import com.nganga.robert.chargelink.ui.viewmodels.HomeScreenViewModel

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    viewModel: HomeScreenViewModel
) {
    navigation(
        startDestination = BottomBarScreen.Home.route,
        route = MAIN_ROUTE
    ){
        composable(route = BottomBarScreen.Home.route){
            HomeScreen(
                viewModel = viewModel,
                onNearByItemClick = { id ->
                    navController.navigate(BottomBarScreen.Details.withArgs(id))
                }
            )
        }
        composable(route = BottomBarScreen.Maps.route){
            MapScreen(viewModel = viewModel)
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
                viewModel = viewModel
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
            StationDetailsScreen(id = entry.arguments?.getString("id"), viewModel = viewModel)
        }
    }
}