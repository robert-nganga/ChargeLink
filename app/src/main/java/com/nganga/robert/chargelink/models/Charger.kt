package com.nganga.robert.chargelink.models

import androidx.compose.ui.graphics.painter.Painter

data class Charger(
    val plug: String,
    val power: String,
    val type: String,
    val image: Painter,
    val isAvailable: Boolean
)
