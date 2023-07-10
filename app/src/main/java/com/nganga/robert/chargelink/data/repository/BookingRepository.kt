package com.nganga.robert.chargelink.data.repository

import com.nganga.robert.chargelink.models.Booking
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface BookingRepository {

    fun getBookingById(id: String): Flow<ResultState<Booking>>

    fun addBookingToDatabase(booking: Booking): Flow<ResultState<String>>

    fun getUserBookings(): Flow<ResultState<List<Booking>>>
}