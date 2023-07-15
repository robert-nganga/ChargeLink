package com.nganga.robert.chargelink.ui.navigation.nav_graph

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nganga.robert.chargelink.ui.navigation.AUTHENTICATION_ROUTE


@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
){

   NavHost(
       navController = navController,
       startDestination = AUTHENTICATION_ROUTE,
       modifier = modifier,
       route = "root"
   ){
       authNavGraph(navController)
       bottomNavGraph(navController)
       bookingNavGraph(navController)

   }
}