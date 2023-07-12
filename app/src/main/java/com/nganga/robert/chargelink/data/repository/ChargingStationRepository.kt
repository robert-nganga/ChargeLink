package com.nganga.robert.chargelink.data.repository

import com.nganga.robert.chargelink.models.NewChargingStation
import com.nganga.robert.chargelink.models.NewUser
import com.nganga.robert.chargelink.models.Review
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface ChargingStationRepository {


    fun submitReview(stationId: String, review: Review): Flow<ResultState<String>>

    fun getCurrentUser(): Flow<ResultState<NewUser>>

    fun getChargingStationById(id: String): Flow<ResultState<NewChargingStation>>

    fun getNearByStations(latitude: Double, longitude: Double): Flow<ResultState<List<NewChargingStation>>>
}