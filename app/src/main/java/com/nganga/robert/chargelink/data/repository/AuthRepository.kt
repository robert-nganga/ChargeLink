package com.nganga.robert.chargelink.data.repository

import android.net.Uri
import com.nganga.robert.chargelink.models.Car
import com.nganga.robert.chargelink.models.User
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun uploadProfileImage(imageUri: Uri): Flow<ResultState<String>>

    fun hasUser():Boolean

    fun logout()

    fun addUserDetails(user: User): Flow<ResultState<String>>

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