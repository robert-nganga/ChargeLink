package com.nganga.robert.chargelink.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.ChargingStation
import com.nganga.robert.chargelink.ui.components.GarageItem
import com.nganga.robert.chargelink.ui.components.NearbyListItem

@Composable
fun HomeScreen(modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        TopAppBar(
            name = "Davido Mnodu",
            location = "Nairobi, Kenya",
            profile = painterResource(id = R.drawable.profile),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(15.dp))
        SearchSection()
        Spacer(modifier = Modifier.height(10.dp))
        GarageItem(
            model = "2022 M4 Competition",
            manufacturer = "BMW M4",
            capacity = "100kMh",
            range = "300km",
            connectors = listOf("AC Type 1", "Type 2"),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "NearBy Stations",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        NearbySection(stations = listOf(
            ChargingStation(
                name = "EvGo Charger",
                location = "Waiyaki way, Westlands",
                rating = "4",
                imageUrl = painterResource(id = R.drawable.station1)
            ),
            ChargingStation(
                name = "Charge Point",
                location = "Garden City, Nairobi",
                rating = "3",
                imageUrl = painterResource(id = R.drawable.station2)
            ),
            ChargingStation(
                name = "Electric Charger",
                location = "Langata rd, Langata",
                rating = "5",
                imageUrl = painterResource(id = R.drawable.station3)
            )
        ))


    }
}

@Composable
fun TopAppBar(
    name: String,
    location: String,
    profile: Painter,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(vertical = 10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "location",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.outline
                    )
                )
            }
        }

        RoundImage(
            image = profile,
            modifier = Modifier.size(70.dp)
        )
    }
}

@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
){
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
            .padding(2.dp)
            .clip(CircleShape)
    )
}

@Composable
fun SearchSection(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(5.dp)
                .weight(8.5f)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Row(
                modifier = modifier.padding(10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search"
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Search")
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .weight(1.5f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onPrimary

            )
        }
    }
}

@Composable
fun NearbySection(
    modifier: Modifier = Modifier,
    stations: List<ChargingStation>
){
    LazyColumn(modifier = modifier){
        items(stations){ station->
            NearbyListItem(
                chargingStation = station,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
        }
    }
}
