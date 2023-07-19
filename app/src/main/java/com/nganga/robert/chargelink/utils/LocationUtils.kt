package com.nganga.robert.chargelink.utils

import android.location.Location
import com.google.android.gms.maps.model.LatLng

object LocationUtils {

    fun Location.toLatLng() = LatLng(this.latitude, this.longitude)
}