package com.nganga.robert.chargelink.models

data class Amenities (
    val wifi: Boolean,
    val restaurants: Boolean,
    val restrooms: Boolean,
    val shops: Boolean,
    val loungeArea: Boolean,
    val maintenance: Boolean,
    val tyrePressure: Boolean
    ){
    constructor():this(
        false,
        false,
        false,
        false,
        false,
        false,
        false
    )
    fun toMap() = hashMapOf(
        "wifi" to wifi,
        "restaurants" to restaurants,
        "restrooms" to restrooms,
        "shops" to shops,
        "loungeArea" to loungeArea,
        "maintenance" to maintenance,
        "tyrePressure" to tyrePressure
    )
}