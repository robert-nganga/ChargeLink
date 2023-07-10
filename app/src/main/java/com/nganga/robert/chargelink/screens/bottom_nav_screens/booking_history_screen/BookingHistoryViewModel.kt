package com.nganga.robert.chargelink.screens.bottom_nav_screens.booking_history_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nganga.robert.chargelink.data.repository.BookingRepository
import com.nganga.robert.chargelink.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class BookingHistoryViewModel@Inject constructor(
    private val bookingRepository: BookingRepository
): ViewModel() {

    private var bookingState = MutableStateFlow(BookingHistoryState())

    val pendingBookings = bookingState.map {
        it.copy(
            bookings = it.bookings.filter { booking -> booking.status == "Pending" }
        )
    }

    val completedBookings = bookingState.map {
        it.copy(
            bookings = it.bookings.filter { booking -> booking.status == "Completed" }
        )
    }

    val canceledBookings = bookingState.map {
        it.copy(
            bookings = it.bookings.filter { booking -> booking.status == "Canceled" }
        )
    }


    fun getUserBookings() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            bookingRepository.getUserBookings().collectLatest { result ->
                when(result.status){
                    ResultState.Status.SUCCESS -> {
                        result.data?.let {
                            bookingState.value = BookingHistoryState(bookings = it)
                        }
                    }
                    ResultState.Status.ERROR -> {
                        bookingState.value = BookingHistoryState(error = result.message!!)
                    }
                    ResultState.Status.LOADING -> {
                        bookingState.value = BookingHistoryState(isLoading = true)
                    }
                }
            }
        }

    }
}