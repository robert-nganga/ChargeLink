package com.nganga.robert.chargelink.models

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class PaymentMethod(
    val icon: ImageVector? = null,
    val title: String = "",
    val image: Painter? = null
)
