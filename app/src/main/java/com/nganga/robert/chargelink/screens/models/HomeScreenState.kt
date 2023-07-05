package com.nganga.robert.chargelink.screens.models

import com.google.android.gms.maps.model.LatLng
import com.nganga.robert.chargelink.models.NewChargingStation
import com.nganga.robert.chargelink.models.NewUser

data class HomeScreenState(
    val nearbyStations: List<NewChargingStation> = emptyList(),
    val currentUser: NewUser = NewUser(),
    val currentLocation: LatLng = LatLng(-1.286389,36.817223),
    val isNearByStationsLoading: Boolean = false,
    val isNearByStationsError: Boolean = false,
)
