package com.nganga.robert.chargelink.ui.screens.statewrappers

import com.nganga.robert.chargelink.models.ChargingStation
import com.nganga.robert.chargelink.models.User

data class HomeScreenState(
    val nearbyStations: List<ChargingStation> = emptyList(),
    val currentUser: User = User(
        name = "",
        email = "",
        image = 0,
        phone = "",
        location = "",
        cars = emptyList()
    )
)
