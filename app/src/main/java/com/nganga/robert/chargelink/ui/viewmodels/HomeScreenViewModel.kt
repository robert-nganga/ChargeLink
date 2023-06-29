package com.nganga.robert.chargelink.ui.viewmodels

import android.location.Location
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.*
import com.nganga.robert.chargelink.repository.ChargingStationRepository
import com.nganga.robert.chargelink.repository.LocationRepository
import com.nganga.robert.chargelink.screens.models.HomeScreenState
import com.nganga.robert.chargelink.screens.models.StationDetailsState
import com.nganga.robert.chargelink.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel@Inject constructor(
    private val locationRepo: LocationRepository,
    private val chargingStationRepo: ChargingStationRepository):ViewModel() {

    var homeScreenState by mutableStateOf(HomeScreenState())
        private set

    var stationDetailsScreenState by mutableStateOf(StationDetailsState())
        private set


    private var _booking = mutableStateOf(emptyBooking)
    val booking: State<Booking> get() = _booking

    init {
        getCurrentUser()
        _booking.value = myBooking
    }

    fun fetchNearbyStations(){
        locationRepo.getLocationOnce { location ->
            getNearbyStations(location)
        }
    }

    fun getStationById(id: String) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            chargingStationRepo.getChargingStationById(id).collect{ result->
                when(result.status){
                    ResultState.Status.SUCCESS -> {
                        val station = result.data
                        station?.let {
                            stationDetailsScreenState = stationDetailsScreenState.copy(
                                chargingStation = it,
                            )
                            Log.i("HomeScreenViewModel", "Station name: ${it.name}")
                        }
                    }
                    ResultState.Status.ERROR -> {
                        Log.i("HomeScreenViewModel", "Error getting station: ${result.message}")
                    }
                    ResultState.Status.LOADING -> {
                    }
                }
            }
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

val myBooking = Booking(
    stationName = "EvGo Charger",
    stationLocation = "Donholm, jogoo road",
    date = "10 June 2023",
    time = "2:00 PM",
    duration = "1 Hour",
    charger = Charger(plug = "CCS 1 DC", power = "360kW", image = R.drawable.ic_ev_plug_ccs2, isAvailable = true)
)

val emptyBooking = Booking(
    stationName = "",
    stationLocation = "",
    date = "",
    time = "",
    duration = "",
    charger = Charger(plug = "", power = "", image = R.drawable.ic_ev_plug_ccs2, isAvailable = true)
)