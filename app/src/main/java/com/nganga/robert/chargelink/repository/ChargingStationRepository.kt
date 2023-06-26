package com.nganga.robert.chargelink.repository

import com.nganga.robert.chargelink.models.NewChargingStation

interface ChargingStationRepository {
    suspend fun addChargingStation(station: NewChargingStation)
    suspend fun addAll()
}