package com.nganga.robert.chargelink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.Booking
import com.nganga.robert.chargelink.models.Charger
import com.nganga.robert.chargelink.ui.components.BookingItem
import com.nganga.robert.chargelink.ui.components.TabView

@Composable
fun BookingsScreen(
    onBackPressed: () -> Unit
){
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }

    var isReminder by rememberSaveable {
        mutableStateOf(false)
    }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        BookingsTopAppBar(
            onBackPressed = onBackPressed,
            title = "My Bookings"
        )
        Spacer(modifier = Modifier.height(20.dp))
        TabView(
            tabTitles = listOf("Upcoming", "Completed", "Canceled"),
            onTabSelected = { selectedTabIndex = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        BookingItem(
            booking = Booking(
                stationName = "EvGo Charger",
                stationLocation = "Donholm, jogoo road",
                date = "10 June 2023",
                time = "2:00 PM",
                duration = "1 Hour",
                charger = Charger(
                    plug = "Tesla",
                    power = "250 kW",
                    isAvailable = true,
                    image = painterResource(id = R.drawable.ic_ev_plug_tesla)
                    
                )
            ), 
            reminder = true, 
            onReminderCheckChanged = {  isReminder = it }
        )
    }

}

@Composable
fun BookingsTopAppBar(
    onBackPressed: ()->Unit,
    title: String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.padding(vertical = 15.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            onClick =  onBackPressed
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Box(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {  }
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search Icon"
            )
        }
    }
}