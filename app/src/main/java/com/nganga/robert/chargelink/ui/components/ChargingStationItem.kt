package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssistantDirection
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.nganga.robert.chargelink.models.ChargingStation
import com.nganga.robert.chargelink.screens.bottom_nav_screens.averageRating


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ChargingStationItem(
    station: ChargingStation,
    modifier: Modifier = Modifier,
    onStationClicked: () -> Unit
){
    androidx.compose.material.Card(
        modifier = modifier
            .clickable {
                onStationClicked.invoke()
            }
            .padding(horizontal = 10.dp),
        elevation = 10.dp, //CardDefaults.cardElevation(defaultElevation = 10.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.background
//        ),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(modifier = Modifier
                .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    model = station.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
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
                        text = station.name,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = station.location,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.outline
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Ratings(
                        rating = station.reviews.averageRating(),
                        starSize = 20.dp,
                        starColor = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    IconText(
                        icon = Icons.Default.AssistantDirection,
                        text = "${station.distance}km/${(station.distance*1.2).toInt()}min",
                        iconSize = 30.dp
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

}