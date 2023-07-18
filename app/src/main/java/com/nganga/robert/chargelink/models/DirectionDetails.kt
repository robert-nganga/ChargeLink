package com.nganga.robert.chargelink.models

import com.google.android.gms.maps.model.LatLng

data class DirectionDetails(
    val points: MutableList<LatLng>,
    val distance: String,
    val duration: String,
    val html: String
){
    constructor(): this(mutableListOf(), "", "", "")
}
