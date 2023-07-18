package com.nganga.robert.chargelink.data.repository

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.nganga.robert.chargelink.models.DirectionDetails
import com.nganga.robert.chargelink.models.PlaceSuggestion
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getDirections(start: LatLng, end: LatLng): Flow<ResultState<DirectionDetails>>

    fun getAddressFromLatLng(latLng: LatLng): String?

    fun requestLocationUpdates(): Flow<Location?>

    fun getLocationOnce(location: (Location)->Unit)

    fun getLatLngFromPlaceId(placeId: String, latLng: (LatLng)->Unit)

    fun searchPlaces(query: String): Flow<ResultState<List<PlaceSuggestion>>>
}