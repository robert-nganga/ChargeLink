package com.nganga.robert.chargelink.screens.models

data class ProfilePhotoState(
    val profileUrl: String? = null,
    val isPhotoUploaded: Boolean = false,
    val isError: Boolean = false,
    val errorMsg: String = "",
    val isLoading: Boolean = false
)
