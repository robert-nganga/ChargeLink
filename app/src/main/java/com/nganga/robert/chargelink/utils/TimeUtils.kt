package com.nganga.robert.chargelink.utils

object TimeUtils {
    //Converts duration in seconds to a string of format hours and minutes
    fun Long.getDurationString(): String {
        val hours = (this / 3600)
        val minutes = (this % 3600) / 60
        return when {
            hours == 0L -> {
                "$minutes min"
            }
            minutes == 0L -> {
                "$hours hrs"
            }
            else -> {
                "$hours hrs $minutes mins"
            }
        }
    }
}