package com.nganga.robert.chargelink.ui.screens.authentication

data class LoginState(
    val isLoginSuccessful: Boolean = false,
    val isError: Boolean = false,
    val errorMsg: String = "",
    val isLoading: Boolean = false
)
