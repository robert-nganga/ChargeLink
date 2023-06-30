package com.nganga.robert.chargelink.repository

import android.location.Location
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getLocationOnce(location: (Location)->Unit)

    fun searchPlaces(query: String): Flow<ResultState<List<AutocompletePrediction>>>
}