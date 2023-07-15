package com.nganga.robert.chargelink.data.repository

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.nganga.robert.chargelink.models.PlaceSuggestion
import com.nganga.robert.chargelink.utils.ResultState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@Suppress("MissingPermission")
class LocationRepositoryImpl@Inject constructor(
    private val placesClient: PlacesClient,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context
): LocationRepository {

    private val autocompleteSessionToken: AutocompleteSessionToken = AutocompleteSessionToken.newInstance()



    override fun getAddressFromLatLng(latLng: LatLng): String? {
        val geocoder = Geocoder(context)
        val address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        return if (address != null && address.isNotEmpty()){
            address[0].getAddressLine(0)
        }else{
            null
        }
    }


    override fun requestLocationUpdates(): Flow<Location?> = callbackFlow{
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                p0.lastLocation?.let {
                    trySend(it)
                }
            }
        }
        val locationRequest = LocationRequest.create().apply {
            interval = 60000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback,
            null)

        awaitClose {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            close()
        }

    }


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