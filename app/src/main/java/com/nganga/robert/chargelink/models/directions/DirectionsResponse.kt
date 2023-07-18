package com.nganga.robert.chargelink.models.directions

import com.google.android.gms.maps.model.Polyline
import com.google.maps.android.PolyUtil
import com.nganga.robert.chargelink.models.DirectionDetails

data class DirectionsResponse(
    val geocoded_waypoints: List<GeocodedWaypoint>,
    val routes: List<Route>,
    val status: String
){
    fun toDirectionDetails(): DirectionDetails{
        return DirectionDetails(
            points = PolyUtil.decode(routes[0].overview_polyline.points),
            distance = routes[0].legs[0].distance.text,
            duration = routes[0].legs[0].duration.text,
            html = routes[0].legs[0].steps[0].html_instructions
        )
    }
}