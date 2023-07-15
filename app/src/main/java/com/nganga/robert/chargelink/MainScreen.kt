package com.nganga.robert.chargelink

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nganga.robert.chargelink.ui.components.HorizontalDivider
import com.nganga.robert.chargelink.ui.navigation.BottomBarScreen
import com.nganga.robert.chargelink.ui.navigation.nav_graph.MainNavGraph


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val showBottomBar = when(navBackStackEntry?.destination?.route?.substringBefore("/")){
        BottomBarScreen.Home.route -> true
        BottomBarScreen.Maps.route -> true
        BottomBarScreen.Bookings.route -> true
        BottomBarScreen.Profile.route -> true
        else -> false
    }

    val enableSystemBarPadding = when(navBackStackEntry?.destination?.route?.substringBefore("/")){
        BottomBarScreen.Maps.route -> false
        else -> true
    }
    Scaffold(
        bottomBar = {
            if (showBottomBar){
                BottomBar(
                    navController = navController,
                    navBackStackEntry = navBackStackEntry,
                    modifier = Modifier
                        .navigationBarsPadding()
                )
            }
        }
    ) { contentPadding ->
        MainNavGraph(
            modifier = if (enableSystemBarPadding)
                Modifier.navigationBarsPadding().statusBarsPadding()
                else Modifier.navigationBarsPadding(),
            navController = navController
        )
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
    modifier: Modifier = Modifier
){
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Maps,
        BottomBarScreen.Bookings,
        BottomBarScreen.Profile,
    )
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colorScheme.background,
        elevation = 5.dp
    ){
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                navController = navController,
                currentDestination = currentDestination
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    navController: NavHostController,
    currentDestination: NavDestination?
){
    val isSelected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true

    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                contentDescription = "Navigation Icon",
                modifier = Modifier.size(26.dp),
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        selected = isSelected,
        onClick = {
            navController.navigate(screen.route){
                popUpTo(BottomBarScreen.Home.route)
                launchSingleTop = true
            }
        },
        alwaysShowLabel = false,
        selectedContentColor = MaterialTheme.colorScheme.primary,
        unselectedContentColor = MaterialTheme.colorScheme.outline
    )

}