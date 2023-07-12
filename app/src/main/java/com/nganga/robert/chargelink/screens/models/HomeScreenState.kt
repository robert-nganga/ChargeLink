package com.nganga.robert.chargelink.screens.models

import com.google.android.gms.maps.model.LatLng
import com.nganga.robert.chargelink.models.ChargingStation
import com.nganga.robert.chargelink.models.User

data class HomeScreenState(
    val nearbyStations: List<ChargingStation> = emptyList(),
    val currentUser: User = User(),
    val currentLocation: LatLng = LatLng(-1.286389,36.817223),
    val isNearByStationsLoading: Boolean = false,
    val isNearByStationsError: Boolean = false,
)
