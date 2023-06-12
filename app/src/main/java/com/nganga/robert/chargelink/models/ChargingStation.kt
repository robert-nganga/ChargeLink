package com.nganga.robert.chargelink.models

import androidx.compose.ui.graphics.painter.Painter

data class ChargingStation(
    val name: String,
    val location: String,
    val rating: String,
    //val description: String,
    val imageUrl: Int,
    //val isAvailable: Boolean
)
