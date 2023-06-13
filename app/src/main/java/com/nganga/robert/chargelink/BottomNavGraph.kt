package com.nganga.robert.chargelink

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nganga.robert.chargelink.ui.screens.*
import com.nganga.robert.chargelink.ui.viewmodels.HomeScreenViewModel


@Composable
fun BottomNavGraph(
    navController: NavHostController,
    viewModel: HomeScreenViewModel
){

    val homeScreenState = viewModel.state
   NavHost(
       navController = navController,
       startDestination = BottomBarScreen.Home.route,
       modifier = Modifier
   ){
       composable(route = BottomBarScreen.Home.route){
           HomeScreen(
               homeScreenState = homeScreenState,
               onNearByItemClick = { id ->
                   navController.navigate(BottomBarScreen.Details.withArgs(id))
               }
           )
       }
       composable(route = BottomBarScreen.Maps.route){
           MapScreen()
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
               onBackPressed = { navController.popBackStack() }
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
           StationDetailsScreen(id = entry.arguments?.getString("id"))
       }


   }
}