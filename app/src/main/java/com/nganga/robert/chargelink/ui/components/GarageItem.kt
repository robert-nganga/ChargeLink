package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SettingsInputSvideo
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
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
    Box(
        modifier = modifier.padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.6f)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clip(RoundedCornerShape(10.dp))
        )
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
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = model,
                        style = MaterialTheme.typography.titleMedium
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
                CarProperties(icon = Icons.Outlined.BatteryChargingFull, property = capacity)
                Spacer(modifier = Modifier.width(15.dp))
                CarProperties(icon = Icons.Default.Speed, property = range)
                Spacer(modifier = Modifier.width(15.dp))
                CarProperties(
                    icon = Icons.Default.SettingsInputSvideo,
                    property = connectors.first()
                )
            }
        }

    }
}

@Composable
fun CarProperties(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    property: String
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = property,
            modifier = Modifier.size(28.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = property,
            style = MaterialTheme.typography.labelMedium
        )
    }
}