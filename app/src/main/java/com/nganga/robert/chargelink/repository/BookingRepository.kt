package com.nganga.robert.chargelink.repository

import com.nganga.robert.chargelink.models.Booking
import com.nganga.robert.chargelink.models.Charger
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface BookingRepository {

    fun addBookingToDatabase(booking: Booking): Flow<ResultState<String>>
}