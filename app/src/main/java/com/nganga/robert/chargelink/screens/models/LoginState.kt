package com.nganga.robert.chargelink.screens.models

data class LoginState(
    val isLoginSuccessful: Boolean = false,
    val isError: Boolean = false,
    val errorMsg: String = "",
    val isLoading: Boolean = false
)
