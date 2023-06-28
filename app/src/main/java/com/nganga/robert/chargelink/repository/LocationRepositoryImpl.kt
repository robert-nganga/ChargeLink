package com.nganga.robert.chargelink.repository

import android.location.Location
import android.location.LocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
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