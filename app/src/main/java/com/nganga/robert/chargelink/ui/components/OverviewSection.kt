package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.models.Amenities

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
        ParkingSection(modifier = Modifier.fillMaxWidth())
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