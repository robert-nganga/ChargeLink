package com.nganga.robert.chargelink.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nganga.robert.chargelink.models.NewChargingStation
import javax.inject.Inject

class ChargingStationRepositoryImpl@Inject constructor(
    private val fireStoreDb: FirebaseFirestore): ChargingStationRepository {


    override suspend fun addAll() {

    }

    override suspend fun addChargingStation(station: NewChargingStation) {

    }

}
