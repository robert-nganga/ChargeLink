package com.nganga.robert.chargelink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.ChargingStation
import com.nganga.robert.chargelink.ui.components.ChargingStationItem

@Composable
fun MapScreen(){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize().padding(bottom = 80.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
           ChargingStationsSection(
               stations = listOf(
                   ChargingStation(
                       name = "EvGo Charger",
                       location = "Waiyaki way, Westlands",
                       averageRating = "4",
                       imageUrl = R.drawable.station1
                   ),
                   ChargingStation(
                       name = "Charge Point",
                       location = "Garden City, Nairobi",
                       averageRating = "3",
                       imageUrl = R.drawable.station2
                   ),
                   ChargingStation(
                       name = "Electric Charger",
                       location = "Langata rd, Langata",
                       averageRating = "5",
                       imageUrl = R.drawable.station3
                   )
               ),
               modifier = Modifier.width(screenWidth - 20.dp)
           )
        }
    }
}

@Composable
fun ChargingStationsSection(
    stations: List<ChargingStation>,
    modifier: Modifier = Modifier
){
    LazyRow(
        contentPadding = PaddingValues(horizontal = 10.dp)
    ){
        items(stations){ station ->
            ChargingStationItem(
                station = station,
                modifier = modifier
            )
        }
    }
}

