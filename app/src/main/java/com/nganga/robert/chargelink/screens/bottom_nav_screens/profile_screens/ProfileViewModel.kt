package com.nganga.robert.chargelink.screens.bottom_nav_screens.profile_screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nganga.robert.chargelink.data.repository.AuthRepository
import com.nganga.robert.chargelink.data.repository.ChargingStationRepository
import com.nganga.robert.chargelink.models.User
import com.nganga.robert.chargelink.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor(
    private val authRepo: AuthRepository
): ViewModel() {

    fun logOut(){
        authRepo.logout()
    }
}