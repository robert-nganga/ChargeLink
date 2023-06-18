package com.nganga.robert.chargelink.ui.screens.authentication



data class RegisterFormState(
    val nameState: TextFieldState,
    val emailState: TextFieldState,
    val genderState: TextFieldState,
    val dobState: TextFieldState
)

data class TextFieldState(
    val text: String = "",
    val hasError: Boolean = false,
    val errorMessage: String = ""
)
