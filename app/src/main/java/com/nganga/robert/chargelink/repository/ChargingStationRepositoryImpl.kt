package com.nganga.robert.chargelink.repository

import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.firebase.firestore.FirebaseFirestore
import com.nganga.robert.chargelink.models.NewChargingStation
import com.nganga.robert.chargelink.ui.viewmodels.amenities
import com.nganga.robert.chargelink.ui.viewmodels.chargers
import com.nganga.robert.chargelink.ui.viewmodels.chargers0
import com.nganga.robert.chargelink.ui.viewmodels.openDays
import com.nganga.robert.chargelink.utils.Constants.CHARGING_STATIONS_COLLECTION_REF
import javax.inject.Inject

class ChargingStationRepositoryImpl@Inject constructor(
    private val fireStoreDb: FirebaseFirestore): ChargingStationRepository {


    override suspend fun addAll() {
        stations.forEach {
            val geoHash = GeoFireUtils.getGeoHashForLocation(GeoLocation(it.lat.toDouble(), it.long.toDouble()))
            addChargingStation(it.copy(geoHash = geoHash))
        }
    }

    override suspend fun addChargingStation(station: NewChargingStation) {
        fireStoreDb.collection(CHARGING_STATIONS_COLLECTION_REF)
            .add(station.toMap())
            .addOnCompleteListener {  }
            .addOnFailureListener {  }

    }




}

// Create a list of 10 Charging Stations
val stations = listOf(
    NewChargingStation(
        id = "1",
        name = "EcoCharge",
        lat = "-1.159016",
        long = "36.642452",
        geoHash = "",
        location = "Ruaka, Limuru rd",
        averageRating = "3",
        description = "EcoCharge is dedicated to providing eco-friendly charging solutions for electric vehicles. Our charging station is conveniently located in Nairobi and offers fast and reliable charging services.",
        imageUrl = "",
        phone = "+254123456789",
        openHours = "24 Hours",
        isAvailable = true,
        amenities = amenities,
        chargers = chargers,
        reviews = emptyList(),
        openDays = openDays
    ),
    NewChargingStation(
        id = "4",
        name = "GreenPower",
        location = "Kilimani, Nairobi",
        lat = "-1.292103",
        long = "36.788802",
        geoHash = "",
        averageRating = "4",
        description = "At GreenPower charging station, we prioritize renewable energy sources to charge your electric vehicle. Our facility is located in a convenient area of Nairobi and offers a range of charging options.",
        imageUrl = "",
        phone = "+254987654321",
        openHours = "8:00 AM - 10:00 PM",
        isAvailable = true,
        amenities = amenities,
        chargers = chargers,
        reviews = emptyList(),
        openDays = openDays
    ),
    NewChargingStation(
        id = "2",
        name = "MajiPower",
        location = "Thika rd, Nairobi",
        lat = "-1.190691",
        long = "36.601120",
        geoHash = "",
        averageRating = "5",
        description = "MajiPower charging station in Mombasa provides efficient charging services for electric vehicles. We prioritize sustainability and offer a comfortable waiting area for our customers.",
        imageUrl = "",
        phone = "+254987654321",
        openHours = "24 Hours",
        isAvailable = true,
        amenities = amenities,
        chargers = chargers,
        reviews = emptyList(),
        openDays = openDays
    ),
    NewChargingStation(
        id = "3",
        name = "SafariCharge",
        location = "Parklands, Westlands",
        lat = "-1.184991",
        long = "36.645001",
        geoHash = "",
        averageRating = "4",
        description = "Experience the convenience of charging your EV at SafariCharge. Located in Nakuru, our charging station offers a tranquil environment and fast charging options.",
        imageUrl = "",
        phone = "+254555555555",
        openHours = "24 Hours",
        isAvailable = false,
        amenities = amenities,
        chargers = chargers,
        reviews = emptyList(),
        openDays = openDays
    )


)
