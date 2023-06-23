package com.nganga.robert.chargelink.models

data class OpenDay(
    val day: String,
    val hours: String
){
    fun toMap() = hashMapOf(
        "day" to day,
        "hours" to hours
    )
}
