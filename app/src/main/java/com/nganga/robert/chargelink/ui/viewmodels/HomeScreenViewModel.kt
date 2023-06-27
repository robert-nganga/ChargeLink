package com.nganga.robert.chargelink.ui.viewmodels

import android.location.Location
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.*
import com.nganga.robert.chargelink.repository.ChargingStationRepository
import com.nganga.robert.chargelink.screens.models.HomeScreenState
import com.nganga.robert.chargelink.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel@Inject constructor(
    private val repository: ChargingStationRepository):ViewModel() {

    var homeScreenState by mutableStateOf(HomeScreenState())
        private set

    private var _booking = mutableStateOf(emptyBooking)
    val booking: State<Booking> get() = _booking

    private var _station = mutableStateOf(emptyStation)
    val station: State<ChargingStation> get() = _station

    fun getChargingStation(id: String){
        val station = chargingStations.find { it.id == id }
        station?.let {
            _station.value = it
        }

        _booking.value = myBooking
    }

    init {
        getCurrentUser()
    }


    private fun getCurrentUser() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            repository.getCurrentUser().collect{ result->
                when(result.status){
                    ResultState.Status.SUCCESS -> {
                        val user = result.data
                        user?.let {
                            homeScreenState = homeScreenState.copy(
                                currentUser = it,
                            )
                            Log.i("HomeScreenViewModel", "CurrentUser email: ${it.email}")
                        }
                    }
                    ResultState.Status.ERROR -> {
                        Log.i("HomeScreenViewModel", "getCurrentUser: ${result.message}")
                    }
                    ResultState.Status.LOADING -> {
                    }
                }
            }
        }
    }

    fun getNearbyStations(location: Location) = viewModelScope.launch {
        Log.i("HomeScreenViewModel", "My Location: ${location.latitude}, ${location.longitude}")
        withContext(Dispatchers.IO){
            repository.getNearByStations(location.latitude, location.longitude).collect{ result->
                when(result.status){
                    ResultState.Status.SUCCESS -> {
                        val stations = result.data
                        stations?.let {
                            homeScreenState = homeScreenState.copy(
                                isNearByStationsLoading = false,
                                isNearByStationsError = false,
                                nearbyStations = it
                            )
                            Log.i("HomeScreenViewModel", "Nearby Stations: ${it.size}")
                        }
                    }
                    ResultState.Status.ERROR -> {
                        homeScreenState = homeScreenState.copy(
                            isNearByStationsLoading = false,
                            isNearByStationsError = true
                        )
                        Log.i("HomeScreenViewModel", "getNearbyStations error:: ${result.message}")
                    }
                    ResultState.Status.LOADING -> {
                        homeScreenState = homeScreenState.copy(
                            isNearByStationsLoading = true
                        )
                    }
                }
            }
        }
    }



}

val myBooking = Booking(
    stationName = "EvGo Charger",
    stationLocation = "Donholm, jogoo road",
    date = "10 June 2023",
    time = "2:00 PM",
    duration = "1 Hour",
    charger = Charger(plug = "CCS 1 DC", power = "360kW", image = R.drawable.ic_ev_plug_ccs2, isAvailable = true)
)

val emptyBooking = Booking(
    stationName = "",
    stationLocation = "",
    date = "",
    time = "",
    duration = "",
    charger = Charger(plug = "", power = "", image = R.drawable.ic_ev_plug_ccs2, isAvailable = true)
)

val emptyStation = ChargingStation(
    id = "",
    name = "",
    location = "",
    averageRating = "",
    description = "",
    imageUrl = 0,
    phone = "",
    openHours = "",
    isAvailable = false,
    chargers = emptyList(),
    reviews = emptyList(),
    openDays = emptyList(),
    amenities = Amenities(false, false, false, false, false, false, false)
)

val openDays = listOf(
    OpenDay(day = "Monday", hours = "08:00 AM - 10:00 PM"),
    OpenDay(day = "Tuesday", hours = "08:00 AM - 10:00 PM"),
    OpenDay(day = "Wednesday", hours = "08:00 AM - 10:00 PM"),
    OpenDay(day = "Thursday", hours = "08:00 AM - 10:00 PM"),
    OpenDay(day = "Friday", hours = "08:00 AM - 10:00 PM"),
    OpenDay(day = "Saturday", hours = "10:00 AM - 6:00 PM"),
)

val chargers = listOf(
    Charger(plug = "CCS 1 DC", power = "360kW", image = R.drawable.ic_ev_plug_ccs2, isAvailable = true
    ),
    Charger(plug = "CCS 2 DC", power = "360kW", image = R.drawable.ic_ev_plug_ccs2_combo, isAvailable = true
    ),
    Charger(plug = "Mennekes (Type 2) AC", power = "22kW", image = R.drawable.ic_ev_plug_iec_mennekes_t2, isAvailable = true
    ),
    Charger(plug = "J1772 (Type 1) AC", power = "19.2kW", image = R.drawable.ic_ev_plug_j1772_t1, isAvailable = true
    ),
    Charger(plug = "Tesla NACS AC/DC", power = "250kW", image = R.drawable.ic_ev_plug_tesla, isAvailable = true
    )
)

val chargers0 = listOf(
    Charger(plug = "CCS 1 DC", power = "360kW", image = 0, isAvailable = true
    ),
    Charger(plug = "CCS 2 DC", power = "360kW", image = 0, isAvailable = true
    ),
    Charger(plug = "Mennekes (Type 2) AC", power = "22kW", image = 0, isAvailable = true
    ),
    Charger(plug = "J1772 (Type 1) AC", power = "19.2kW", image = 0, isAvailable = true
    ),
    Charger(plug = "Tesla NACS AC/DC", power = "250kW", image = 0, isAvailable = true
    )
)

val reviews = listOf(
    Review(
        userName = "John Doe",
        userImage = R.drawable.user1,
        date = "2023-06-04",
        time = "11:02 AM",
        message = "This was a great product! I would definitely recommend it to others.",
        rating = 5
    ),
    Review(
        userName = "Jane Doe",
        userImage = R.drawable.user5,
        date = "2023-06-03",
        time = "10:00 AM",
        message = "This product was not as good as I expected. I would not recommend it to others.",
        rating = 2
    ),
    Review(
        userName = "Bill Smith",
        userImage = R.drawable.user2,
        date = "2023-06-02",
        time = "9:00 AM",
        message = "This product was okay. I wouldn't say it was great, but it wasn't bad either.",
        rating = 3
    ),
    Review(
        userName = "Susan Jones",
        userImage = R.drawable.user4,
        date = "2023-06-01",
        time = "8:00 AM",
        message = "I loved this product! It was everything I was looking for and more.",
        rating = 4
    ),
    Review(
        userName = "David Brown",
        userImage = R.drawable.user3,
        date = "2023-05-31",
        time = "7:00 AM",
        message = "This product was a lifesaver! I don't know what I would have done without it.",
        rating = 5
    )
)

val amenities = Amenities(
    wifi = true,
    restaurants = true,
    restrooms = false,
    tyrePressure = true,
    loungeArea = false,
    maintenance = true,
    shops = false
)


val chargingStations = listOf(
    ChargingStation(
        id = "1",
        name = "EcoCharge",
        location = "Ruaka, Limuru rd",
        averageRating = "3",
        description = "EcoCharge is dedicated to providing eco-friendly charging solutions for electric vehicles. Our charging station is conveniently located in Nairobi and offers fast and reliable charging services.",
        imageUrl = R.drawable.station1,
        phone = "+254123456789",
        openHours = "24 Hours",
        isAvailable = true,
        amenities = amenities,
        chargers = chargers,
        reviews = reviews,
        openDays = openDays
    ),
    ChargingStation(
        id = "2",
        name = "MajiPower",
        location = "Thika rd, Nairobi",
        averageRating = "5",
        description = "MajiPower charging station in Mombasa provides efficient charging services for electric vehicles. We prioritize sustainability and offer a comfortable waiting area for our customers.",
        imageUrl = R.drawable.station2,
        phone = "+254987654321",
        openHours = "24 Hours",
        isAvailable = true,
        amenities = amenities,
        chargers = chargers,
        reviews = reviews,
        openDays = openDays
    ),
    ChargingStation(
        id = "3",
        name = "SafariCharge",
        location = "Parklands, Westlands",
        averageRating = "4",
        description = "Experience the convenience of charging your EV at SafariCharge. Located in Nakuru, our charging station offers a tranquil environment and fast charging options.",
        imageUrl = R.drawable.station3,
        phone = "+254555555555",
        openHours = "24 Hours",
        isAvailable = false,
        amenities = amenities,
        chargers = chargers,
        reviews = reviews,
        openDays = openDays
    )
)