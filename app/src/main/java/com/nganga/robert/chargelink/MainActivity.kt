package com.nganga.robert.chargelink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nganga.robert.chargelink.ui.screens.authentication.OtpVerificationScreen
import com.nganga.robert.chargelink.ui.screens.authentication.RegisterUserScreen
import com.nganga.robert.chargelink.ui.theme.ChargeLinkTheme
import com.nganga.robert.chargelink.ui.viewmodels.AuthenticationViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChargeLinkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    tonalElevation = 5.dp,
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
    ChargeLinkTheme {
        RegisterUserScreen(AuthenticationViewModel())
    }
}