package com.nganga.robert.chargelink.models

import androidx.compose.ui.graphics.painter.Painter

data class User(
    val name: String,
    val email: String,
    //drawable resource of user e.g user1
    val image: Int,
    val phone: String,
    val location: String,
    val cars: List<Car>
)
