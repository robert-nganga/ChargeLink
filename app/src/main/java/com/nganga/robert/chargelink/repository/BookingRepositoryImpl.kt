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


    override fun getChargers(): Flow<ResultState<List<Charger>>> = callbackFlow {
        trySend(ResultState.loading())

        fireStoreDb.collection(CHARGERS_COLLECTION_REF).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val chargers = task.result?.toObjects(Charger::class.java)
                    trySend(ResultState.success(chargers))
                }
            }
            .addOnFailureListener {
                trySend(ResultState.error(it.message.toString() ?: "Unknown error"))
            }
    }
}