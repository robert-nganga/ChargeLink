package com.nganga.robert.chargelink.models

data class Car(
    val manufacturer: String,
    // drawable resource e.g car1
    val imageUrl: Int,
    val model: String,
    val batteryCapacity: String,
    val range: String,
    val plug: String,
    val chargingSpeed: String
)
