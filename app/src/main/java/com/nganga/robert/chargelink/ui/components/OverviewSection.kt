package com.nganga.robert.chargelink.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.models.Amenities
import com.nganga.robert.chargelink.models.OpenDay

@Composable
fun OverviewSection(
    modifier: Modifier = Modifier,
    description: String,
    phone: String,
    openHours: String,
    openDays: List<String>,
    amenities: Amenities
){
    Column(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Phone",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(5.dp))
        IconText(icon = Icons.Default.Phone, text = "  $phone", iconSize = 32.dp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "About",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        ParkingSection(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))
        OpenHoursCard(
            openHours = "12 Hours",
            openDays = listOf(
                OpenDay(day = "Monday", hours = "08:00 AM - 10:00 PM"),
                OpenDay(day = "Tuesday", hours = "08:00 AM - 10:00 PM"),
                OpenDay(day = "Wednesday", hours = "08:00 AM - 10:00 PM"),
                OpenDay(day = "Thursday", hours = "08:00 AM - 10:00 PM"),
                OpenDay(day = "Friday", hours = "08:00 AM - 10:00 PM"),
                OpenDay(day = "Saturday", hours = "10:00 AM - 6:00 PM"),
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ParkingSection(
    modifier: Modifier = Modifier
){
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Parking",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "Pay",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Divider(
                thickness = 0.8.dp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Cost",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Free for clients",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
fun OpenHoursCard(
    modifier: Modifier = Modifier,
    openHours: String,
    openDays: List<OpenDay>
){
    Card(modifier = modifier) {
        OpenHoursCardContent(
            openHours = openHours,
            openDays = openDays,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun OpenHoursCardContent(
    modifier: Modifier = Modifier,
    openHours: String,
    openDays: List<OpenDay>
){
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconText(
                icon = Icons.Outlined.Timer,
                text = " Open",
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = openHours,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
            Box(modifier = Modifier.weight(1f))
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        "show less"
                    } else {
                        "show more"
                    }
                )
            }
        }
        if (expanded){
            openDays.forEach { openDay->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = openDay.day,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.outline
                        )
                    )
                    Text(
                        text = openDay.day,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}