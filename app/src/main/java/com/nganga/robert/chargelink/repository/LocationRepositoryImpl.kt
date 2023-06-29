package com.nganga.robert.chargelink.repository

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import javax.inject.Inject

@Suppress("MissingPermission")
class LocationRepositoryImpl@Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
): LocationRepository {

    override fun getLocationOnce(location: (Location) -> Unit) {
        fusedLocationProviderClient.lastLocation
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    location(task.result)
                }
            }
    }
}