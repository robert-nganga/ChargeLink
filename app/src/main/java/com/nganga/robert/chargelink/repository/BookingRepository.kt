package com.nganga.robert.chargelink.repository

import com.nganga.robert.chargelink.models.Charger
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface BookingRepository {

    fun getChargers(): Flow<ResultState<List<Charger>>>
}