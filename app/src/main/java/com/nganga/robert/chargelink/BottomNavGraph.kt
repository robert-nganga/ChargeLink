package com.nganga.robert.chargelink

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nganga.robert.chargelink.ui.screens.*


@Composable
fun BottomNavGraph(
    navController: NavHostController
){
   NavHost(
       navController = navController,
       startDestination = BottomBarScreen.Home.route,
       modifier = Modifier
   ){
       composable(route = BottomBarScreen.Home.route){
           HomeScreen()
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
       composable(route = BottomBarScreen.Details.route){
           StationDetailsScreen()
       }


   }
}