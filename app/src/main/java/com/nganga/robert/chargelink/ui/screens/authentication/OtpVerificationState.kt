package com.nganga.robert.chargelink.ui.screens.authentication

data class OtpVerificationState(
    val otpCode: String = "",
    val isError: Boolean = false,
    val errorMsg: String = "",
    val isSignInSuccessful: Boolean = false,
    val isLoading: Boolean = false
)
