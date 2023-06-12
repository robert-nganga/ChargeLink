package com.nganga.robert.chargelink.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.Car
import com.nganga.robert.chargelink.models.ChargingStation
import com.nganga.robert.chargelink.models.User
import com.nganga.robert.chargelink.ui.screens.statewrappers.HomeScreenState

class HomeScreenViewModel:ViewModel() {

    private var _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> get() = _state

    init {
        _state.value = HomeScreenState(
            nearbyStations = chargingStations,
            currentUser = user
        )
    }

}

val user = User(
    name = "Beatrice Muthoni",
    email = "beat.mut@gmail.com",
    image = R.drawable.user1,
    phone = "013-456-7890",
    location = "Donholm, Nairobi",
    cars = listOf(
        Car(
            manufacturer = "Tesla",
            imageUrl = R.drawable.bmw,
            model = "Model 3",
            batteryCapacity = "75 kWh",
            range = "358 miles",
            plug = "Type 2",
            chargingSpeed = "150 kW"
        )
    )
)

val chargingStations = listOf(
    ChargingStation(
        name = "EvGo Charger",
        location = "Waiyaki way, Westlands",
        rating = "4",
        imageUrl = R.drawable.station1
    ),
    ChargingStation(
        name = "Charge Point",
        location = "Garden City, Nairobi",
        rating = "3",
        imageUrl = R.drawable.station2
    ),
    ChargingStation(
        name = "Electric Charger",
        location = "Langata rd, Langata",
        rating = "5",
        imageUrl = R.drawable.station3
    )
)