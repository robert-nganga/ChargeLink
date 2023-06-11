package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SettingsInputSvideo
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R

@Composable
fun GarageItem(
    modifier: Modifier = Modifier,
    manufacturer: String,
    model: String,
    capacity: String,
    range: String,
    connectors: List<String>

){
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        border = null,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )

    ) {
        Box(
            modifier = Modifier.padding(8.dp).fillMaxSize()
        ) {
//            Box(
//                modifier = Modifier
//                    .align(Alignment.TopStart)
//                    .fillMaxWidth(0.7f)
//                    .fillMaxHeight(0.6f)
//                    .background(MaterialTheme.colorScheme.surfaceVariant)
//                    .clip(RoundedCornerShape(10.dp))
//            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = manufacturer,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = model,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                    Image(
                        painter = painterResource(id = R.drawable.bmw),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .fillMaxHeight(0.7f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconText(icon = Icons.Outlined.BatteryChargingFull, text = capacity)
                    Spacer(modifier = Modifier.width(15.dp))
                    IconText(icon = Icons.Default.Speed, text = range)
                    Spacer(modifier = Modifier.width(15.dp))
                    IconText(
                        icon = Icons.Default.SettingsInputSvideo,
                        text = connectors.first()
                    )
                }
            }

        }
    }

}