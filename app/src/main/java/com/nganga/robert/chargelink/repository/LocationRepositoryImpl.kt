package com.nganga.robert.chargelink.repository

import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.nganga.robert.chargelink.models.PlaceSuggestion
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@Suppress("MissingPermission")
class LocationRepositoryImpl@Inject constructor(
    private val placesClient: PlacesClient,
    private val fusedLocationProviderClient: FusedLocationProviderClient
): LocationRepository {

    private val autocompleteSessionToken: AutocompleteSessionToken = AutocompleteSessionToken.newInstance()

    override fun getLocationOnce(location: (Location) -> Unit) {
        fusedLocationProviderClient.lastLocation
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    location(task.result)
                }
            }
    }

    override fun getLatLngFromPlaceId(placeId: String, latLng: (LatLng) -> Unit) {
        val placeFields = listOf(Place.Field.ID, Place.Field.LAT_LNG)

        val request = FetchPlaceRequest.newInstance(placeId, placeFields)
        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                val place = response.place
                place.latLng?.let { latLng.invoke(it) }
                Log.i("LocationRepositoryImpl", "Place found: ${place.name}")
            }

    }

    override fun searchPlaces(query: String): Flow<ResultState<List<PlaceSuggestion>>> = callbackFlow {
        trySend(ResultState.loading())
        getLocationOnce { location ->
            val request = FindAutocompletePredictionsRequest.builder()
                .setCountries("KE")
                .setOrigin(LatLng(location.latitude, location.longitude))
                .setSessionToken(autocompleteSessionToken)
                .setQuery(query)
                .build()

            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    val places = response.autocompletePredictions.map { prediction ->
                        val primary = prediction.getFullText(null).toString().substringBefore(",")
                        val secondary = prediction.getFullText(null).toString().substringAfter(",")
                        PlaceSuggestion(
                            placeId = prediction.placeId,
                            primaryText = primary,
                            secondaryText = secondary
                        ) }
                    trySend(ResultState.success(places))
                }
                .addOnFailureListener { exception ->
                    trySend(ResultState.error( exception.message?: "Unknown error"))
                }
        }
        awaitClose {
            close()
        }
    }


}