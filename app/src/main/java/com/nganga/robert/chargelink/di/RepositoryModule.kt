package com.nganga.robert.chargelink.di

import com.nganga.robert.chargelink.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun providesFirebaseAuthRepository(
        repo: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @ViewModelScoped
    abstract fun providesChargingStationRepository(
        repo: ChargingStationRepositoryImpl
    ): ChargingStationRepository

    @Binds
    @ViewModelScoped
    abstract fun providesLocationRepository(
        repo: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    @ViewModelScoped
    abstract fun providesBookingRepository(
        repo: BookingRepositoryImpl
    ): BookingRepository


}