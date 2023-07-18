package com.nganga.robert.chargelink.data.remote

import com.nganga.robert.chargelink.BuildConfig
import com.nganga.robert.chargelink.models.directions.DirectionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionService {


    @GET("maps/api/directions/json")
    suspend fun getDirection(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") apiKey: String = BuildConfig.GOOGLE_MAPS_API_KEY
    ): Response<DirectionsResponse>
}