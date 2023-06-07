package com.nganga.robert.chargelink.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.ChargingStation
import com.nganga.robert.chargelink.ui.components.ChargingStationItem

@Composable
fun MapScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            ChargingStationItem(
                station = ChargingStation(
                    name = "Electric Charger",
                    location = "Langata rd, Langata",
                    rating = "4",
                    imageUrl = painterResource(id = R.drawable.station3)
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ChargingStationItem(
    stations: List<ChargingStation>,
    modifier: Modifier = Modifier
){
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ){

    }
}

