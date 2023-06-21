package com.nganga.robert.chargelink.di

import com.nganga.robert.chargelink.repository.AuthRepository
import com.nganga.robert.chargelink.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providesFirebaseAuthRepository(
        repo: AuthRepositoryImpl
    ): AuthRepository
}