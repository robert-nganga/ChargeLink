package com.nganga.robert.chargelink.screens.bottom_nav_screens.profile_screens

import androidx.lifecycle.ViewModel
import com.nganga.robert.chargelink.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor(
    private val authRepo: AuthRepository
): ViewModel() {

    fun logOut(){
        authRepo.logout()
    }
}