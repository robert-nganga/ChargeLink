package com.nganga.robert.chargelink.screens.bottom_nav_screens.home_screen

import android.location.Location
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.nganga.robert.chargelink.data.preferences.UserPreferencesRepository
import com.nganga.robert.chargelink.data.repository.ChargingStationRepository
import com.nganga.robert.chargelink.data.repository.LocationRepository
import com.nganga.robert.chargelink.models.Booking
import com.nganga.robert.chargelink.screens.models.HomeScreenState
import com.nganga.robert.chargelink.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel@Inject constructor(
    private val locationRepo: LocationRepository,
    userPreferencesRepository: UserPreferencesRepository,
    private val chargingStationRepo: ChargingStationRepository):ViewModel() {

    val radiusPreference = userPreferencesRepository.radius.asLiveData()

    var homeScreenState by mutableStateOf(HomeScreenState())
        private set

    private val currentLocation = locationRepo.requestLocationUpdates().asLiveData()

    val userAddress = currentLocation.map { location ->
        location?.let {
            locationRepo.getAddressFromLatLng(LatLng(it.latitude, it.longitude))
        } ?: ""
    }


    private var _booking = mutableStateOf(Booking())
    val booking: State<Booking> get() = _booking


    init {
        getCurrentUser()

        //Updates the list of stations when the radius preference changes
        radiusPreference.map {
            fetchNearbyStations(it)
        }
    }



    fun fetchNearbyStations(radius: Float){
        locationRepo.getLocationOnce { location ->
            homeScreenState = homeScreenState.copy(
                currentLocation = LatLng(location.latitude, location.longitude)
            )
            getNearbyStations(location, radius)
        }
    }



    private fun getCurrentUser() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            chargingStationRepo.getCurrentUser().collect { result ->
                when (result.status) {
                    ResultState.Status.SUCCESS -> {
                        val user = result.data
                        user?.let {
                            homeScreenState = homeScreenState.copy(
                                currentUser = it,
                            )
                            Log.i("HomeScreenViewModel", "CurrentUser email: ${it.email}")
                        }
                    }
                    ResultState.Status.ERROR -> {
                        Log.i("HomeScreenViewModel", "getCurrentUser: ${result.message}")
                    }
                    ResultState.Status.LOADING -> {
                    }
                }
            }
        }
    }

    private fun getNearbyStations(location: Location, radius: Float) = viewModelScope.launch {
        Log.i("HomeScreenViewModel", "My Location: ${location.latitude}, ${location.longitude}")
        withContext(Dispatchers.IO){
            chargingStationRepo.getNearByStations(location, radius).collect{ result->
                when(result.status){
                    ResultState.Status.SUCCESS -> {
                        val stations = result.data
                        stations?.let {
                            homeScreenState = homeScreenState.copy(
                                isNearByStationsLoading = false,
                                isNearByStationsError = false,
                                nearbyStations = it
                            )
                            Log.i("HomeScreenViewModel", "Nearby Stations: ${it.size}")
                        }
                    }
                    ResultState.Status.ERROR -> {
                        homeScreenState = homeScreenState.copy(
                            isNearByStationsLoading = false,
                            isNearByStationsError = true
                        )
                        Log.i("HomeScreenViewModel", "getNearbyStations error:: ${result.message}")
                    }
                    ResultState.Status.LOADING -> {
                        homeScreenState = homeScreenState.copy(
                            isNearByStationsLoading = true
                        )
                    }
                }
            }
        }
    }
}