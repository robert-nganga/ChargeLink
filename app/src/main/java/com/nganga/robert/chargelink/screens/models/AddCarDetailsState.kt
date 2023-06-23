package com.nganga.robert.chargelink.screens.models

data class AddCarDetailsState(
    val isCarAddedSuccessfully: Boolean = false,
    val isError: Boolean = false,
    val errorMsg: String = "",
    val isLoading: Boolean = false
)
