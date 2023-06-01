package com.nganga.robert.chargelink.models

data class Car(
    val manufacturer: String,
    val imageUrl: String,
    val model: String,
    val batteryCapacity: String,
    val range: String,
    val plugTypes: List<String>,
    val type: String,
    val connectors: List<String>,
    val chargingSpeed: String
)
