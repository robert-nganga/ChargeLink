package com.nganga.robert.chargelink.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nganga.robert.chargelink.models.Booking
import com.nganga.robert.chargelink.models.Charger
import com.nganga.robert.chargelink.utils.Constants.BOOKINGS_COLLECTION_REF
import com.nganga.robert.chargelink.utils.Constants.CHARGERS_COLLECTION_REF
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import ulid.ULID
import java.util.*
import javax.inject.Inject

class BookingRepositoryImpl@Inject constructor(
    private val fireStoreDb: FirebaseFirestore,
    private val auth: FirebaseAuth
): BookingRepository {



    override fun addBookingToDatabase(booking: Booking): Flow<ResultState<String>> = callbackFlow {
        val newBooking = booking.copy(
            userId = auth.currentUser?.uid!!,
            status = "Pending",
            bookingId = ULID.randomULID()
        )
        fireStoreDb.collection(BOOKINGS_COLLECTION_REF).document(newBooking.bookingId)
            .set(newBooking.toMap())
            .addOnSuccessListener {
                trySend(ResultState.success("Booking added successfully"))
            }
            .addOnFailureListener {
                trySend(ResultState.error(it.message ?: "Booking failed"))
            }
        awaitClose {
            close()
        }
    }

    override fun getUserBookings(): Flow<ResultState<List<Booking>>> = callbackFlow {
        fireStoreDb.collection(BOOKINGS_COLLECTION_REF)
            .whereEqualTo("userId", auth.currentUser?.uid)
            .addSnapshotListener { value, error ->
                if (error != null){
                    trySend(ResultState.error(error.message ?: "Unknown Error occurred"))
                    return@addSnapshotListener
                }
                val bookings = value?.toObjects(Booking::class.java) ?: emptyList()
                trySend(ResultState.success(bookings))
            }
        awaitClose {
            close()
        }
    }

}