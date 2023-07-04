package com.nganga.robert.chargelink.ui.viewmodels

import android.location.Location
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.*
import com.nganga.robert.chargelink.repository.ChargingStationRepository
import com.nganga.robert.chargelink.repository.LocationRepository
import com.nganga.robert.chargelink.screens.models.HomeScreenState
import com.nganga.robert.chargelink.screens.models.PlaceSuggestionsState
import com.nganga.robert.chargelink.screens.models.StationDetailsState
import com.nganga.robert.chargelink.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel@Inject constructor(
    private val locationRepo: LocationRepository,
    private val chargingStationRepo: ChargingStationRepository):ViewModel() {

    var homeScreenState by mutableStateOf(HomeScreenState())
        private set

    var stationDetailsScreenState by mutableStateOf(StationDetailsState())
        private set


    private var _booking = mutableStateOf(Booking())
    val booking: State<Booking> get() = _booking



    fun submitReview(stationId: String, rating:Int, message: String) = viewModelScope.launch{
        val current = LocalDateTime.now()
        val review = Review(
            userName = homeScreenState.currentUser.name,
            userImage = 0,
            date = current.format(DateTimeFormatter.ofPattern("MM/dd/yy")),
            time = current.format(DateTimeFormatter.ofPattern("hh:mm a")),
            message = message,
            rating = rating
        )
        Log.i("HomeScreenViewModel", "Review: ${review.userName}")
        chargingStationRepo.submitReview(stationId, review).collect{ result->
            when(result.status){
                ResultState.Status.SUCCESS -> {
                    Log.i("HomeScreenViewModel", "Review submitted successfully")
                }
                ResultState.Status.ERROR -> {
                    Log.i("HomeScreenViewModel", "Error submitting review: ${result.message}")
                }
                ResultState.Status.LOADING -> {
                }
            }
        }
    }

    fun fetchNearbyStations(){
        locationRepo.getLocationOnce { location ->
            homeScreenState = homeScreenState.copy(
                currentLocation = LatLng(location.latitude, location.longitude)
            )
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
                            Log.i("HomeScreenViewModel", "Station id: ${it.id}")
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