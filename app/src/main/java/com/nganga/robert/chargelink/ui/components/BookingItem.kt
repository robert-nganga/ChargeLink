package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.Booking

@Composable
fun BookingItem(
    booking: Booking,
    reminder: Boolean,
    onReminderCheckChanged: (Boolean)->Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = booking.date,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = booking.time,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Box(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.remind_me),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
            Spacer(modifier = Modifier.width(5.dp))
            Switch(
                checked = reminder,
                onCheckedChange = { onReminderCheckChanged(it) }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Column {
                Text(
                    text = booking.stationName,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = booking.stationLocation,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.outline
                    )
                )
            }
            Box(modifier = Modifier.weight(1f))
            BoxIcon(icon = Icons.Default.Navigation)
        }
    }
}

@Composable
fun BoxIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconSize: Dp = 28.dp,
    padding: Dp = 10.dp
    ){
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(iconSize),
            tint = MaterialTheme.colorScheme.onPrimary

        )
    }
}