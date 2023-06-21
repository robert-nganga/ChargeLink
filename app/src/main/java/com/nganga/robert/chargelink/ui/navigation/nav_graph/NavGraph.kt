package com.nganga.robert.chargelink

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nganga.robert.chargelink.ui.navigation.nav_graph.AUTHENTICATION_ROUTE
import com.nganga.robert.chargelink.ui.navigation.nav_graph.authNavGraph
import com.nganga.robert.chargelink.ui.navigation.nav_graph.mainNavGraph
import com.nganga.robert.chargelink.ui.viewmodels.HomeScreenViewModel


@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: HomeScreenViewModel
){

   NavHost(
       navController = navController,
       startDestination = AUTHENTICATION_ROUTE,
       modifier = Modifier,
       route = "root"
   ){
       authNavGraph(navController)
       mainNavGraph(navController, viewModel)


   }
}