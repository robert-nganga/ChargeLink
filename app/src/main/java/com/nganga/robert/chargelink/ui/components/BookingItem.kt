package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.Booking
import com.nganga.robert.chargelink.utils.IconUtils.getChargerIcon
import com.nganga.robert.chargelink.utils.TimeUtils.getDurationString

@Composable
fun BookingItem(
    booking: Booking,
    modifier: Modifier = Modifier,
    onBookingClicked: () -> Unit
){
    var reminder by remember{
        mutableStateOf(false)
    }
    Card(
        modifier = modifier.padding(horizontal = 12.dp, vertical = 5.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
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
                if (booking.status == "Pending") {
                    Text(
                        text = stringResource(id = R.string.remind_me),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.outline
                        )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Switch(
                        checked = reminder,
                        onCheckedChange = {
                            reminder = it
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(thickness = 0.5.dp)
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
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextColumn(
                        modifier = Modifier.weight(1f),
                        headerText = booking.charger.plug,
                        trailingText = "",
                        icon = painterResource(id = getChargerIcon(booking.charger.plug))
                    )
                    TextColumn(
                        modifier = Modifier.weight(1f),
                        headerText = stringResource(id = R.string.max_power),
                        trailingText = booking.charger.power,
                    )
                    TextColumn(
                        modifier = Modifier.weight(1f),
                        headerText = stringResource(id = R.string.duration),
                        trailingText = booking.duration.getDurationString(),
                    )
//                    TextColumn(
//                        headerText = stringResource(id = R.string.price),
//                        trailingText = "Ksh 1000",
//                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(10.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {  }
                ) {
                    Text(text = stringResource(id = R.string.cancel_booking))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onBookingClicked.invoke()
                    }
                ) {
                    Text(text = stringResource(id = R.string.view))
                }
            }

        }
    }
}

@Composable
fun TextColumn(
    headerText: String,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    trailingText: String,
){
    Column(
        modifier = modifier.padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = headerText,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.outline
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        if (icon != null){
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }else{
            Text(
                text = trailingText,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
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