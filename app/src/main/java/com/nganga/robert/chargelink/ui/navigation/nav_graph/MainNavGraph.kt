package com.nganga.robert.chargelink.ui.navigation.nav_graph

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nganga.robert.chargelink.ui.navigation.AUTHENTICATION_ROUTE
import com.nganga.robert.chargelink.ui.navigation.BOTTOM_NAV_ROUTE
import com.nganga.robert.chargelink.ui.viewmodels.HomeScreenViewModel


@Composable
fun MainNavGraph(
    navController: NavHostController,
    activity: Activity
){

   NavHost(
       navController = navController,
       startDestination = BOTTOM_NAV_ROUTE,
       modifier = Modifier,
       route = "root"
   ){
       authNavGraph(navController)
       bottomNavGraph(navController, activity)

   }
}