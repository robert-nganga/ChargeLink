package com.nganga.robert.chargelink.screens.booking_screens

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nganga.robert.chargelink.models.Booking
import com.nganga.robert.chargelink.models.Charger
import com.nganga.robert.chargelink.models.ChargingStation
import com.nganga.robert.chargelink.models.PaymentMethod
import com.nganga.robert.chargelink.data.repository.BookingRepository
import com.nganga.robert.chargelink.data.repository.ChargingStationRepository
import com.nganga.robert.chargelink.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookingViewModel@Inject constructor(
    private val bookingRepo: BookingRepository,
    private val chargingStationRepo: ChargingStationRepository
): ViewModel() {

    var station by mutableStateOf(ChargingStation())
        private set

    var bookingState by mutableStateOf(BookingState())
        private set

    var paymentMethod by mutableStateOf(PaymentMethod())
        private set



    fun updatePaymentMethod(method: PaymentMethod){
        paymentMethod = method
    }

    fun setBookingPrice(price: String){
        val booking = bookingState.booking.copy(totalPrice = price)
        bookingState = bookingState.copy(booking = booking)
    }

    fun setBookingDetails(date: String, time: String, duration: Long){
        val booking = bookingState.booking.copy(
            date = date,
            time = time,
            duration = duration
        )
        bookingState = bookingState.copy(booking = booking)
    }

    fun setBookingCharger(charger: Charger){
        val booking = bookingState.booking.copy(charger = charger)
        bookingState = bookingState.copy(booking = booking)
    }

    fun getBookingById(id: String) = viewModelScope.launch {
        bookingRepo.getBookingById(id).collectLatest { result->
            when (result.status){
                ResultState.Status.SUCCESS -> {
                    bookingState = bookingState.copy(
                        booking = result.data!!,
                        isLoading = false,
                        errorMessage = ""
                    )
                }
                ResultState.Status.ERROR -> {
                    bookingState = bookingState.copy(
                        errorMessage = result.message!!,
                        isLoading = false
                    )
                }
                ResultState.Status.LOADING -> {
                    bookingState = bookingState.copy(
                        isLoading = true
                    )
                }
            }
        }
    }

    fun addBookingToDatabase() = viewModelScope.launch {
        bookingRepo.addBookingToDatabase(bookingState.booking).collectLatest { result->
            when (result.status){
                ResultState.Status.SUCCESS -> {
                    bookingState = bookingState.copy(
                        booking = Booking(),
                        isLoading = false,
                        errorMessage = "",
                        isAddedToDbSuccessfully = true
                    )
                }
                ResultState.Status.ERROR -> {
                    bookingState = bookingState.copy(
                        errorMessage = result.message!!,
                        isLoading = false,
                        isAddedToDbSuccessfully = false
                    )
                }
                ResultState.Status.LOADING -> {
                    bookingState = bookingState.copy(
                        isLoading = true
                    )
                }
            }
        }
    }

    fun getStation(id: String) = viewModelScope.launch {
        chargingStationRepo.getChargingStationById(id).collectLatest { result->
            result.data?.let {
                station = it
                val booking = bookingState.booking.copy(
                    stationName = it.name,
                    stationLocation = it.location,
                    stationId = it.id
                )
                bookingState = bookingState.copy(booking = booking)

            }
        }
    }

}