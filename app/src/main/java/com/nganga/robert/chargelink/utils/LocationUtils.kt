package com.nganga.robert.chargelink.utils

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

object LocationUtils {

    fun Location.toLatLng() = LatLng(this.latitude, this.longitude)

    fun List<LatLng>.toLatLngBounds(): LatLngBounds {

        val initialBounds = LatLngBounds(this[0], this[0])

        return this.drop(1).fold(initialBounds) { bounds, latLng ->
            val minLat = minOf(bounds.southwest.latitude, latLng.latitude)
            val minLng = minOf(bounds.southwest.longitude, latLng.longitude)
            val maxLat = maxOf(bounds.northeast.latitude, latLng.latitude)
            val maxLng = maxOf(bounds.northeast.longitude, latLng.longitude)

            LatLngBounds(LatLng(minLat, minLng), LatLng(maxLat, maxLng))
        }
    }
}