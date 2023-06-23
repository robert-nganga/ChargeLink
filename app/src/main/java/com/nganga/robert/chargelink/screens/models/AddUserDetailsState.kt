package com.nganga.robert.chargelink.screens.models

data class AddUserDetailsState(
    val isUserAddedSuccessfully: Boolean = false,
    val isError: Boolean = false,
    val errorMsg: String = "",
    val isLoading: Boolean = false
)
