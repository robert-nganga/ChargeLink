package com.nganga.robert.chargelink.models

data class NewChargingStation(
    val id: String,
    val name: String,
    val lat: String,
    val long: String,
    val geoHash: String,
    val location: String,
    val averageRating: String,
    val description: String,
    val imageUrl: String,
    val phone: String,
    val openHours: String,
    val isAvailable: Boolean,
    val chargers: List<Charger>,
    val reviews: List<Review>,
    val openDays: List<OpenDay>,
    val amenities: Amenities
){
    fun toMap() = hashMapOf(
        "id" to id,
        "name" to name,
        "lat" to lat,
        "long" to long,
        "geoHash" to geoHash,
        "location" to location,
        "averageRating" to averageRating,
        "description" to description,
        "imageUrl" to imageUrl,
        "phone" to phone,
        "openHours" to openHours,
        "isAvailable" to isAvailable,
        "chargers" to chargers.map { it.toMap() },
        "reviews" to reviews.map { it.toMap() },
        "openDays" to openDays.map { it.toMap() },
        "amenities" to amenities.toMap()
    )
}
