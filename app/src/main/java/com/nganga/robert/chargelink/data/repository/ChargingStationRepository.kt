package com.nganga.robert.chargelink.data.repository

import com.nganga.robert.chargelink.models.ChargingStation
import com.nganga.robert.chargelink.models.User
import com.nganga.robert.chargelink.models.Review
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface ChargingStationRepository {


    fun submitReview(stationId: String, review: Review): Flow<ResultState<String>>

    fun getCurrentUser(): Flow<ResultState<User>>

    fun getChargingStationById(id: String): Flow<ResultState<ChargingStation>>

    fun getNearByStations(latitude: Double, longitude: Double): Flow<ResultState<List<ChargingStation>>>
}