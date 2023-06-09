package com.nganga.robert.chargelink

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.BookOnline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val title: String,
    val route: String,
    val icon: ImageVector
){
    object Home: BottomBarScreen(
        title = "Home",
        route = "home",
        icon = Icons.Outlined.Home
    )
    object Maps: BottomBarScreen(
        title = "Map",
        route = "map",
        icon = Icons.Outlined.Map
    )
    object Bookings: BottomBarScreen(
        title = "Bookings",
        route = "bookings",
        icon = Icons.Outlined.BookOnline
    )
    object Profile: BottomBarScreen(
        title = "Profile",
        route = "profile",
        icon = Icons.Outlined.AccountCircle
    )
}
