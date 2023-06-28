package com.nganga.robert.chargelink.repository

import android.location.Location

interface LocationRepository {

    fun getLocationOnce(location: (Location)->Unit)
}