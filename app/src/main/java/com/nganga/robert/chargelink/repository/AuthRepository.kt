package com.nganga.robert.chargelink.repository

import com.nganga.robert.chargelink.models.Car
import com.nganga.robert.chargelink.models.NewUser
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun hasUser():Boolean

    fun logout()

    fun addUserDetails(user: NewUser): Flow<ResultState<String>>

    fun addUserCarDetails(car: Car): Flow<ResultState<String>>

    fun createUser(
        email: String,
        password: String
    ): Flow<ResultState<String>>

    fun login(
        email: String,
        password: String
    ): Flow<ResultState<String>>
}