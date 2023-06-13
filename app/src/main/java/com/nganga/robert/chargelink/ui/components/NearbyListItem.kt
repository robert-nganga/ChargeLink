package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.models.ChargingStation

@Composable
fun NearbyListItem(
    chargingStation: ChargingStation,
    modifier: Modifier = Modifier,
    onNearByItemClick: (String)->Unit
){
    Card(
        modifier = modifier
            .padding(bottom = 10.dp)
            .clickable {
                   onNearByItemClick(chargingStation.id)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = chargingStation.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .weight(3f)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.weight(7f)
            ) {
                Text(
                    text = chargingStation.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = chargingStation.location,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.outline
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                IconText(icon = Icons.Filled.Star, text = chargingStation.averageRating)

            }
        }
    }
}