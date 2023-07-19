package com.nganga.robert.chargelink.screens.bottom_nav_screens.route_screen

import com.google.android.gms.maps.model.LatLng

data class PolyLinePointsState(
    val points: List<LatLng> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""

)
