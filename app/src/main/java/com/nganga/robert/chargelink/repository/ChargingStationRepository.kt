package com.nganga.robert.chargelink.repository

import com.nganga.robert.chargelink.models.NewChargingStation
import com.nganga.robert.chargelink.models.NewUser
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface ChargingStationRepository {


    suspend fun getCurrentUser(): Flow<ResultState<NewUser>>

    suspend fun getNearByStations(latitude: Double, longitude: Double): Flow<ResultState<List<NewChargingStation>>>
}