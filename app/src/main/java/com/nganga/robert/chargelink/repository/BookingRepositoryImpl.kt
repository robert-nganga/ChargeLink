package com.nganga.robert.chargelink.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nganga.robert.chargelink.models.Charger
import com.nganga.robert.chargelink.utils.Constants.CHARGERS_COLLECTION_REF
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class BookingRepositoryImpl@Inject constructor(
    private val fireStoreDb: FirebaseFirestore
): BookingRepository {

}