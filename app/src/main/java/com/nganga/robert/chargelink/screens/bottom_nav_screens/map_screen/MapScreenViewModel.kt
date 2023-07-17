package com.nganga.robert.chargelink.screens.bottom_nav_screens.map_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.nganga.robert.chargelink.data.preferences.UserPreferencesRepository
import com.nganga.robert.chargelink.data.repository.ChargingStationRepository
import com.nganga.robert.chargelink.data.repository.LocationRepository
import com.nganga.robert.chargelink.screens.models.PlaceSuggestionsState
import com.nganga.robert.chargelink.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel@Inject constructor(
    private val locationRepo: LocationRepository,
    userPreferencesRepository: UserPreferencesRepository,
    private val chargingStationRepo: ChargingStationRepository
): ViewModel() {

    private val radiusPreference = userPreferencesRepository.radius.asLiveData()

    var placeSuggestionsState by mutableStateOf(PlaceSuggestionsState())
        private set

    var nearbyStationsState by mutableStateOf(NearbyStationsState())
        private set



    fun onQueryChange(query: String){
        placeSuggestionsState = placeSuggestionsState.copy(
            query = query
        )
        if(query.isNotEmpty()){
            searchPlaces(query)
        }
    }

    fun clearSuggestions(){
        placeSuggestionsState = placeSuggestionsState.copy(
            suggestions =  emptyList()
        )
    }

    fun clearLocation(){
        nearbyStationsState = nearbyStationsState.copy(
            location = null
        )
    }

    fun getCoordinatesFromPlaceId(placeId: String){
        locationRepo.getLatLngFromPlaceId(placeId){ latLng->
            nearbyStationsState = nearbyStationsState.copy(
                location = latLng
            )
            updateNearbyStations(latLng)
        }

    }

    private fun updateNearbyStations(location: LatLng) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            chargingStationRepo.getNearByStations(location, radiusPreference.value ?: 15.0f).collect{ result->
                when (result.status){
                    ResultState.Status.SUCCESS -> {
                        result.data?.let {
                            nearbyStationsState = nearbyStationsState.copy(
                                nearbyStations = it,
                                isLoading = false,
                                isError = false,
                                errorMsg = ""
                            )
                        }
                    }
                    ResultState.Status.LOADING ->{
                        nearbyStationsState = nearbyStationsState.copy(isLoading = true)
                    }
                    ResultState.Status.ERROR -> {
                        nearbyStationsState = nearbyStationsState.copy(
                            nearbyStations = emptyList(),
                            isLoading = false,
                            isError = true,
                            errorMsg = result.message!!
                        )
                    }
                }
            }
        }
    }

    private fun searchPlaces(query: String) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            locationRepo.searchPlaces(query).collect { result->
                when(result.status){
                    ResultState.Status.SUCCESS -> {
                        val suggestions = result.data
                        suggestions?.let {
                            placeSuggestionsState = placeSuggestionsState.copy(
                                suggestions = it,
                                isLoading = false,
                                error = ""
                            )
                            Log.i("HomeScreenViewModel", "Suggestions: ${it.size}")
                        }
                    }
                    ResultState.Status.ERROR -> {
                        placeSuggestionsState = placeSuggestionsState.copy(
                            suggestions = emptyList(),
                            isLoading = false,
                            error = result.message!!
                        )
                        Log.i("HomeScreenViewModel", "Error searching places: ${result.message}")
                    }
                    ResultState.Status.LOADING -> {
                        placeSuggestionsState = placeSuggestionsState.copy(
                            isLoading = true,
                        )
                    }
                }
            }
        }
    }



}