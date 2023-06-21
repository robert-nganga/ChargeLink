package com.nganga.robert.chargelink.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.nganga.robert.chargelink.repository.AuthRepository
import com.nganga.robert.chargelink.ui.screens.authentication.LoginState

class AuthenticationViewModel(
    private val repository: AuthRepository = AuthRepository()
): ViewModel() {

    var loginState by mutableStateOf(LoginState())
        private set


    fun onLoginClicked(email: String, password: String){

    }

    fun onSignUpClicked(email: String, password: String){

    }
}