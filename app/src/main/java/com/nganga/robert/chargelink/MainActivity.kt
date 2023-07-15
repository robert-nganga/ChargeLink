package com.nganga.robert.chargelink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nganga.robert.chargelink.screens.booking_screens.BookingConfirmationScreen
import com.nganga.robert.chargelink.screens.booking_screens.EnterBookingDetailsScreen
import com.nganga.robert.chargelink.screens.booking_screens.PaymentDetailsScreen
import com.nganga.robert.chargelink.ui.theme.ChargeLinkTheme
import com.nganga.robert.chargelink.ui.theme.UserPreferencesViewModel
import com.nganga.robert.chargelink.utils.ThemeSelection
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val userPreferencesViewModel = hiltViewModel<UserPreferencesViewModel>()
            val preferences = userPreferencesViewModel.userPreferences.observeAsState()
            val appTheme = preferences.value?.appTheme ?: ThemeSelection.USE_SYSTEM_SETTINGS

            // Update the system bars to be translucent
            val systemUiController = rememberSystemUiController()
            val isSystemDark = isSystemInDarkTheme()
            val navigationBarColor = MaterialTheme.colorScheme.background
            LaunchedEffect(key1 = appTheme, key2 = navigationBarColor) {
                val useDarkIcons = when (appTheme){
                    ThemeSelection.DARK_MODE -> false
                    ThemeSelection.LIGHT_MODE -> true
                    ThemeSelection.USE_SYSTEM_SETTINGS -> !isSystemDark
                    else -> !isSystemDark
                }
                systemUiController.setStatusBarColor(Color.Transparent, darkIcons = useDarkIcons)
                systemUiController.setNavigationBarColor(navigationBarColor, darkIcons = useDarkIcons)
            }

            ChargeLinkTheme(
                darkTheme = when (appTheme){
                    ThemeSelection.DARK_MODE -> true
                    ThemeSelection.LIGHT_MODE -> false
                    ThemeSelection.USE_SYSTEM_SETTINGS -> isSystemInDarkTheme()
                    else -> isSystemInDarkTheme()
                }
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}