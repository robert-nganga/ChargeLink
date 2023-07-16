package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Ratings(
    modifier: Modifier = Modifier,
    rating: Int,
    starSize: Dp,
    starColor: Color,
    onRatingChanged: ((rating: Int) -> Unit)? = null
){
    Row(
        modifier = modifier.padding(3.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for(x in 1 .. 5){
            Icon(
                imageVector = if (x <= rating) Icons.Rounded.StarRate else Icons.Rounded.StarOutline,
                contentDescription = null,
                modifier = Modifier
                    .size(starSize)
                    .clickable {
                           if (onRatingChanged != null){
                               onRatingChanged(x)
                           }
                    },
                tint = starColor
            )
        }
    }
}