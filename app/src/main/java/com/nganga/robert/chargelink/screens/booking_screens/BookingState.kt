package com.nganga.robert.chargelink.screens.booking_screens

import com.nganga.robert.chargelink.models.Booking

data class BookingState(
    val booking: Booking = Booking(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val isAddedToDbSuccessfully: Boolean = false,
)
