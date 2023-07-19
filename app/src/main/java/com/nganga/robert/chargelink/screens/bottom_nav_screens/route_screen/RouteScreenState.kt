package com.nganga.robert.chargelink.screens.bottom_nav_screens.route_screen

import com.google.android.gms.maps.model.LatLng

data class RouteScreenState(
    val isRouteActive: Boolean = false,
    val startLocation: LatLng = LatLng(0.0, 0.0),
    val endLocation: LatLng = LatLng(0.0, 0.0),
    val polyLinePointsState: PolyLinePointsState = PolyLinePointsState(),
)
