package com.nganga.robert.chargelink.screens.booking_screens

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nganga.robert.chargelink.models.Booking
import com.nganga.robert.chargelink.models.Charger
import com.nganga.robert.chargelink.models.NewChargingStation
import com.nganga.robert.chargelink.repository.BookingRepository
import com.nganga.robert.chargelink.repository.ChargingStationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookingViewModel@Inject constructor(
    //private val bookingRepo: BookingRepository,
    private val chargingStationRepo: ChargingStationRepository
): ViewModel() {

    var station by mutableStateOf(NewChargingStation())
        private set

    var booking by mutableStateOf(Booking())
        private set



    fun setBookingPrice(price: String){
        booking = booking.copy(totalPrice = price)
    }

    fun setBookingDetails(date: String, time: String, duration: Long){
        booking = booking.copy(
            date = date,
            time = time,
            duration = duration
        )
    }

    fun setBookingCharger(charger: Charger){
        booking = booking.copy(charger = charger)
    }

    fun getStation(id: String) = viewModelScope.launch {
        chargingStationRepo.getChargingStationById(id).collectLatest { result->
            result.data?.let {
                station = it
                booking = booking.copy(
                    stationName = it.name,
                    stationLocation = it.location,
                    stationId = it.id
                )
            }

        }
    }

}