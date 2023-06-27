package com.nganga.robert.chargelink.screens.models

import com.nganga.robert.chargelink.models.ChargingStation
import com.nganga.robert.chargelink.models.NewChargingStation
import com.nganga.robert.chargelink.models.NewUser
import com.nganga.robert.chargelink.models.User

data class HomeScreenState(
    val nearbyStations: List<NewChargingStation> = emptyList(),
    val currentUser: NewUser = NewUser(),
    val isNearByStationsLoading: Boolean = false,
    val isNearByStationsError: Boolean = false,
)
