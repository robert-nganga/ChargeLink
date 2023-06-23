package com.nganga.robert.chargelink.models

import androidx.compose.ui.graphics.painter.Painter

data class Review(
    val userName: String,
    val userImage: Int,
    val date: String,
    val time: String,
    val message: String,
    val rating: Int
){
    fun toMap() = hashMapOf(
        "userName" to userName,
        "userImage" to userImage,
        "date" to date,
        "time" to time,
        "message" to message,
        "rating" to rating
    )
}
