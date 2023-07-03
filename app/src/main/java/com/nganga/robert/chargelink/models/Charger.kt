package com.nganga.robert.chargelink.models

data class Charger(
    val plug: String,
    val power: String,
    val image: Int,
    val isAvailable: Boolean,
    val id: String = ""
){
    constructor():this(
        "",
        "",
        0,
        false,
        ""
    )
    fun toMap() = hashMapOf(
        "plug" to plug,
        "power" to power,
        "image" to image,
        "isAvailable" to isAvailable,
        "id" to id
    )
}
