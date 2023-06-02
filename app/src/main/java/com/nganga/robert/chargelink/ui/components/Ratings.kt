package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Ratings(
    modifier: Modifier = Modifier,
    rating: Float,
    starSize: Dp,
    starColor: Color
){
    Row(
        modifier = modifier.padding(3.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var x = rating
        while (x > 0){
            if (x > 1){
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier.size(starSize),
                    tint = starColor
                )
            }else{
                Icon(
                    imageVector = Icons.Filled.StarHalf,
                    contentDescription = null,
                    modifier = Modifier.size(starSize),
                    tint = starColor
                )
            }
            x -= 1
        }
    }
}