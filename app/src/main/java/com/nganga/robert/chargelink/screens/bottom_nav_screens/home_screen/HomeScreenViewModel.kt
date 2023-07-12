package com.nganga.robert.chargelink.screens.bottom_nav_screens.home_screen

import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.nganga.robert.chargelink.models.*
import com.nganga.robert.chargelink.data.repository.ChargingStationRepository
import com.nganga.robert.chargelink.data.repository.LocationRepository
import com.nganga.robert.chargelink.screens.models.HomeScreenState
import com.nganga.robert.chargelink.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel@Inject constructor(
    private val locationRepo: LocationRepository,
    private val chargingStationRepo: ChargingStationRepository):ViewModel() {

    var homeScreenState by mutableStateOf(HomeScreenState())
        private set

    private val currentLocation = locationRepo.requestLocationUpdates()

    val userAddress = currentLocation.map {
        it?.let { location ->
            locationRepo.getAddressFromLatLng(LatLng(location.latitude, location.longitude))
        }?: ""
    }



    private var _booking = mutableStateOf(Booking())
    val booking: State<Booking> get() = _booking


    init {
        getCurrentUser()
    }



    fun fetchNearbyStations(){
        locationRepo.getLocationOnce { location ->
            homeScreenState = homeScreenState.copy(
                currentLocation = LatLng(location.latitude, location.longitude)
            )
            getNearbyStations(location)
        }
    }



    private fun getCurrentUser() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            chargingStationRepo.getCurrentUser().collect{ result->
                when(result.status){
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

    private fun getNearbyStations(location: Location) = viewModelScope.launch {
        Log.i("HomeScreenViewModel", "My Location: ${location.latitude}, ${location.longitude}")
        withContext(Dispatchers.IO){
            chargingStationRepo.getNearByStations(location.latitude, location.longitude).collect{ result->
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