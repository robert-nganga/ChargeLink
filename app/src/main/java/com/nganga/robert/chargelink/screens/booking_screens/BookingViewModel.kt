package com.nganga.robert.chargelink.screens.booking_screens

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nganga.robert.chargelink.models.Charger
import com.nganga.robert.chargelink.repository.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookingViewModel@Inject constructor(
    private val bookingRepo: BookingRepository
): ViewModel() {

    var chargers by mutableStateOf<List<Charger>>(listOf())
    private set


    fun getChargers() = viewModelScope.launch {
        bookingRepo.getChargers().collectLatest {
            chargers = it.data ?: listOf()
        }
    }
}