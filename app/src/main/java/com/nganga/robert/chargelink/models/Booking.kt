package com.nganga.robert.chargelink.models

import java.util.*

data class Booking(
    val bookingId: String,
    val stationName: String,
    val stationLocation: String,
    val stationId: String,
    val date: String,
    val time: String,
    val charger: Charger,
    val duration: Long,
    val userId: String,
    val totalPrice: String,
    val status: String
){
    constructor():this(
        bookingId = "",
        stationName = "",
        stationLocation = "",
        stationId = "",
        date = "",
        time = "",
        charger = Charger(),
        duration = 0L,
        userId = "",
        totalPrice = "",
        status = ""
    )

    fun toMap() = hashMapOf(
        "bookingId" to bookingId,
        "stationName" to stationName,
        "stationLocation" to stationLocation,
        "stationId" to stationId,
        "date" to date,
        "time" to time,
        "charger" to charger,
        "duration" to duration,
        "userId" to userId,
        "totalPrice" to totalPrice,
        "status" to status
    )
}
