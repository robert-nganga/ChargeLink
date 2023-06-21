package com.nganga.robert.chargelink.repository

import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun createUser(
        email: String,
        password: String
    ): Flow<ResultState<String>>

    fun login(
        email: String,
        password: String
    ): Flow<ResultState<String>>
}