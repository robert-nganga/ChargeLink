package com.nganga.robert.chargelink.screens.bottom_nav_screens.booking_history_screen

import com.nganga.robert.chargelink.models.Booking

data class BookingHistoryState(
    val bookings: List<Booking> = listOf(),
    val isLoading: Boolean = false,
    val error: String = ""
)
