package com.nganga.robert.chargelink.screens.bottom_nav_screens.map_screen

import com.google.android.gms.maps.model.LatLng
import com.nganga.robert.chargelink.models.NewChargingStation

data class NearbyStationsState(
    val nearbyStations: List<NewChargingStation> = emptyList(),
    val location: LatLng? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMsg: String = ""
)
