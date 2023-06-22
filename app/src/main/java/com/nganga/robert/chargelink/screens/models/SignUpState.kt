package com.nganga.robert.chargelink.screens.models

data class SignUpState(
    val isSignUpSuccessful: Boolean = false,
    val isError: Boolean = false,
    val errorMsg: String = "",
    val isLoading: Boolean = false
)
