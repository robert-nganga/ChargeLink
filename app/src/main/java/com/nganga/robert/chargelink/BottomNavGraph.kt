package com.nganga.robert.chargelink

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nganga.robert.chargelink.ui.screens.BookingsScreen
import com.nganga.robert.chargelink.ui.screens.HomeScreen
import com.nganga.robert.chargelink.ui.screens.MapScreen
import com.nganga.robert.chargelink.ui.screens.ProfileScreen


@Composable
fun BottomNavGraph(
    navController: NavHostController,
    contentPadding: PaddingValues
){
   NavHost(
       navController = navController,
       startDestination = BottomBarScreen.Home.route,
       modifier = Modifier.padding(contentPadding)
   ){
       composable(route = BottomBarScreen.Home.route){
           HomeScreen()
       }
       composable(route = BottomBarScreen.Maps.route){
           MapScreen()
       }
       composable(route = BottomBarScreen.Profile.route){
           ProfileScreen()
       }
       composable(route = BottomBarScreen.Bookings.route){
           BookingsScreen(
               onBackPressed = { navController.popBackStack() }
           )
       }

   }
}