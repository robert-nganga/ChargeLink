package com.nganga.robert.chargelink.screens.bottom_nav_screens.route_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.nganga.robert.chargelink.data.repository.LocationRepository
import com.nganga.robert.chargelink.models.PlaceSuggestion
import com.nganga.robert.chargelink.utils.LocationUtils.toLatLng
import com.nganga.robert.chargelink.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RouteViewModel@Inject constructor(
    private val locationRepo: LocationRepository
): ViewModel() {

    val currentLocation = locationRepo.requestLocationUpdates().asLiveData()

    var routeScreenState by mutableStateOf(RouteScreenState())
        private set

    var placeSuggestions by mutableStateOf(emptyList<PlaceSuggestion>())
        private set

    var startQuery by mutableStateOf("")
        private set

    var endQuery by mutableStateOf("")
        private set

    fun clearSuggestions(){
        placeSuggestions = emptyList()
    }

    fun onStartQueryChange(query: String) {
        startQuery = query
        searchPlaces(query)
    }

    fun onEndQueryChange(query: String) {
        endQuery = query
        searchPlaces(query)
    }

    fun updateStartLocation(placeName: String, placeId: String){
        locationRepo.getLatLngFromPlaceId(placeId){ latLng ->
            startQuery = placeName
            routeScreenState = routeScreenState.copy(
                startLocation = latLng
            )
        }
    }

    fun clearRoute(){
        routeScreenState = routeScreenState.copy(
            isRouteActive = false,
            startLocation = LatLng(0.0, 0.0),
            endLocation = LatLng(0.0, 0.0),
            polyLinePointsState = PolyLinePointsState()
        )
        startQuery = ""
        endQuery = ""
        placeSuggestions = emptyList()
    }

    fun updateEndLocation(placeName: String, placeId: String){
        locationRepo.getLatLngFromPlaceId(placeId){ latLng ->
            endQuery = placeName
            routeScreenState = routeScreenState.copy(
                endLocation = latLng,
                isRouteActive = true
            )
            if (startQuery.isEmpty()){
                locationRepo.getLocationOnce { currentLocation ->
                    routeScreenState = routeScreenState.copy(
                        startLocation = currentLocation.toLatLng()
                    )
                    getDirections(currentLocation.toLatLng(), latLng)
                    Log.i("RouteViewModel", "called getDirections with start:: ${currentLocation.toLatLng()} and end:: $latLng")
                }
            }else{
                getDirections(routeScreenState.startLocation, latLng)
                Log.i("RouteViewModel", "called getDirections with start:: ${routeScreenState.startLocation} and end:: $latLng")
            }
        }
    }

    private fun getDirections(startLocation: LatLng, endLocation: LatLng) = viewModelScope.launch {
        locationRepo.getDirections(startLocation, endLocation).collect { result ->
            when (result.status) {
                ResultState.Status.SUCCESS -> {
                    result.data?.let {
                        val polyState = routeScreenState.polyLinePointsState.copy(
                            points = it.points,
                            isLoading = false,
                            error = ""
                        )
                        routeScreenState = routeScreenState.copy(
                            polyLinePointsState = polyState,
                        )
                        Log.i("RouteViewModel", "getDirections: ${it.points.size}")
                    }
                }
                ResultState.Status.ERROR -> {
                    val polyState = routeScreenState.polyLinePointsState.copy(
                        points = emptyList(),
                        isLoading = false,
                        error = result.message ?: "An unknown error occurred"
                    )
                    routeScreenState = routeScreenState.copy(
                        polyLinePointsState = polyState,
                    )
                    Log.i("RouteViewModel", "getDirections: ${result.message}")
                }
                ResultState.Status.LOADING -> {
                    val polyState = routeScreenState.polyLinePointsState.copy(
                        isLoading = true
                    )
                    routeScreenState = routeScreenState.copy(
                        polyLinePointsState = polyState,
                    )
                }
            }
        }
    }

    private fun searchPlaces(query: String) = viewModelScope.launch {
        locationRepo.searchPlaces(query).collect { result ->
            when (result.status) {
                ResultState.Status.SUCCESS -> {
                    result.data?.let {
                        placeSuggestions = it
                    }
                }
                ResultState.Status.ERROR -> {}
                ResultState.Status.LOADING -> {}
            }
        }
    }
}