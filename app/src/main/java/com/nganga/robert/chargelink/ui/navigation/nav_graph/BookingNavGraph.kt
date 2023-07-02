package com.nganga.robert.chargelink.ui.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.nganga.robert.chargelink.ui.navigation.BOOKING_ROUTE
import com.nganga.robert.chargelink.ui.navigation.BookingScreen


fun NavGraphBuilder.bookingNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = BookingScreen.SelectCharger.route,
        route = BOOKING_ROUTE
    ){}
}