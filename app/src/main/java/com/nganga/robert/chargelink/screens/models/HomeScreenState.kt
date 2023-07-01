package com.nganga.robert.chargelink.screens.models

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.nganga.robert.chargelink.models.ChargingStation
import com.nganga.robert.chargelink.models.NewChargingStation
import com.nganga.robert.chargelink.models.NewUser
import com.nganga.robert.chargelink.models.User

data class HomeScreenState(
    val nearbyStations: List<NewChargingStation> = emptyList(),
    val currentUser: NewUser = NewUser(),
    val currentLocation: LatLng = LatLng(-1.286389,36.817223),
    val isNearByStationsLoading: Boolean = false,
    val isNearByStationsError: Boolean = false,
)
