package com.nganga.robert.chargelink.screens.models

import com.nganga.robert.chargelink.models.NewChargingStation

data class StationDetailsState(
    val chargingStation: NewChargingStation = NewChargingStation(),
)
