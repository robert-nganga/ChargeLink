package com.nganga.robert.chargelink.di

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.nganga.robert.chargelink.BuildConfig
import com.nganga.robert.chargelink.data.remote.DirectionService
import com.nganga.robert.chargelink.data.repository.LocationRepository
import com.nganga.robert.chargelink.data.repository.LocationRepositoryImpl
import com.nganga.robert.chargelink.utils.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object LocationModule {

    @Provides
    @Singleton
    fun provideGeocoder(@ApplicationContext context: Context) =
        Geocoder(context)

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context) =
        LocationServices.getFusedLocationProviderClient(context)


    @Provides
    @Singleton
    fun providePlacesClient(@ApplicationContext context: Context): PlacesClient {
        Places.initialize(context, BuildConfig.GOOGLE_MAPS_API_KEY)
        return Places.createClient(context)
    }

    @Provides
    @Singleton
    fun provideDirectionService(): DirectionService {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.DIRECTION_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(DirectionService::class.java)
    }

    @Provides
    @Singleton
    fun providesLocationRepository(
        placesClient: PlacesClient,
        fusedLocationProviderClient: FusedLocationProviderClient,
        directionService: DirectionService,
        geocoder: Geocoder
    ): LocationRepository{
        return LocationRepositoryImpl(placesClient, fusedLocationProviderClient, directionService, geocoder)
    }
}

