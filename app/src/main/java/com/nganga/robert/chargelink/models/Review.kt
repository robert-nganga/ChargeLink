package com.nganga.robert.chargelink.models

import androidx.compose.ui.graphics.painter.Painter

data class Review(
    val userName: String,
    val userImage: Painter,
    val date: String,
    val time: String,
    val message: String,
    val rating: Float
)
