package com.nganga.robert.chargelink.screens.bottom_nav_screens.station_details_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nganga.robert.chargelink.data.repository.ChargingStationRepository
import com.nganga.robert.chargelink.models.Review
import com.nganga.robert.chargelink.models.User
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
class StationDetailsViewModel @Inject constructor(
    private val chargingStationRepo: ChargingStationRepository
): ViewModel() {

    var stationDetailsScreenState by mutableStateOf(StationDetailsState())
        private set

    var currentUser by mutableStateOf(User())
        private set

    init {
        getCurrentUser()
    }

    fun getAverageRating(reviews: List<Review>): Int {
        return reviews.sumOf { it.rating } / reviews.size
    }


    fun submitReview(stationId: String, rating:Int, message: String) = viewModelScope.launch{
        val current = LocalDateTime.now()
        val review = Review(
            userName = currentUser.name,
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

    private fun getCurrentUser() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            chargingStationRepo.getCurrentUser().collect{ result->
                result.data?.let {
                    currentUser = it
                }
            }
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
}