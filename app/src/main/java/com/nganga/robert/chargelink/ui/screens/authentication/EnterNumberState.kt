package com.nganga.robert.chargelink.ui.screens.authentication

data class EnterNumberState(
    val number: String = "",
    val isError: Boolean = false,
    val errorText: String = ""
)
