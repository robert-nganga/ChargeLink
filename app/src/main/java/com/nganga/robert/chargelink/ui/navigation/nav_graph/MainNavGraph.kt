package com.nganga.robert.chargelink.ui.navigation.nav_graph

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nganga.robert.chargelink.ui.navigation.AUTHENTICATION_ROUTE


@Composable
fun MainNavGraph(
    navController: NavHostController
){

   NavHost(
       navController = navController,
       startDestination = AUTHENTICATION_ROUTE,
       modifier = Modifier,
       route = "root"
   ){
       authNavGraph(navController)
       bottomNavGraph(navController)
       bookingNavGraph(navController)

   }
}