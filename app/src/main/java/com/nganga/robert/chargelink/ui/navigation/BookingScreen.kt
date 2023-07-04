package com.nganga.robert.chargelink.ui.navigation


const val BOOKING_ROUTE = "booking"
sealed class BookingScreen(val route: String) {
    object SelectCharger: BookingScreen(route = "selectCharger")
    object EnterBookingDetails: BookingScreen(route = "enterBookingDetails")
    object PaymentDetails: BookingScreen(route = "paymentDetails")
    object BookingConfirmation: BookingScreen(route = "bookingConfirmation")
}