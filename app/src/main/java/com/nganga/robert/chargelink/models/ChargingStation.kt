package com.nganga.robert.chargelink.models

data class ChargingStation(
    val id: String,
    val name: String,
    val location: String,
    val averageRating: String,
    val description: String,
    //drawable resource e.g station1
    val imageUrl: Int,
    val phone: String,
    val openHours: String,
    val isAvailable: Boolean,
    val chargers: List<Charger>,
    val reviews: List<Review>,
    val openDays: List<OpenDay>,
    val amenities: Amenities
)
