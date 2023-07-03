package com.nganga.robert.chargelink.screens.booking_screens

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nganga.robert.chargelink.models.Charger
import com.nganga.robert.chargelink.models.NewChargingStation
import com.nganga.robert.chargelink.repository.BookingRepository
import com.nganga.robert.chargelink.repository.ChargingStationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookingViewModel@Inject constructor(
    //private val bookingRepo: BookingRepository,
    private val chargingStationRepo: ChargingStationRepository
): ViewModel() {

    var station by mutableStateOf(NewChargingStation())
        private set



    fun getStation(id: String) = viewModelScope.launch {
        chargingStationRepo.getChargingStationById(id).collectLatest {
            station = it.data ?: NewChargingStation()
        }
    }

}