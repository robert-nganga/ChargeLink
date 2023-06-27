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
){
    constructor(): this("",
        0,
        "",
        "",
        "",
        "",
        "")
    fun toMap() = hashMapOf(
        "manufacturer" to manufacturer,
        "imageUrl" to imageUrl,
        "model" to model,
        "batteryCapacity" to batteryCapacity,
        "range" to range,
        "plug" to plug,
        "chargingSpeed" to chargingSpeed
    )
}
