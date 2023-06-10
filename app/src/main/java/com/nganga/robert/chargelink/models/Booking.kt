package com.nganga.robert.chargelink.models

data class Booking(
    val stationName: String,
    val stationLocation: String,
    val date: String,
    val time: String,
    val charger: Charger,
    val duration: String
)
