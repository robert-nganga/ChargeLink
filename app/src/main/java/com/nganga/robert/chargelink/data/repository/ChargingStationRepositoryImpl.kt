package com.nganga.robert.chargelink.data.repository

import android.util.Log
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.nganga.robert.chargelink.models.ChargingStation
import com.nganga.robert.chargelink.models.User
import com.nganga.robert.chargelink.models.Review
import com.nganga.robert.chargelink.utils.Constants.CHARGING_STATIONS_COLLECTION_REF
import com.nganga.robert.chargelink.utils.Constants.USERS_COLLECTION_REF
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.collections.ArrayList

class ChargingStationRepositoryImpl@Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStoreDb: FirebaseFirestore): ChargingStationRepository {


    override fun submitReview(
        stationId: String,
        review: Review
    ): Flow<ResultState<String>> = callbackFlow {
        fireStoreDb.collection(CHARGING_STATIONS_COLLECTION_REF).document(stationId.trim())
            .update("reviews", FieldValue.arrayUnion(review.toMap()))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(ResultState.success("Review submitted successfully"))
                }
            }
            .addOnFailureListener {
                trySend(ResultState.error(it.message?: "Unknown error"))
            }

        awaitClose {
            close()
        }
    }


    override fun getCurrentUser(): Flow<ResultState<User>> = callbackFlow{
        fireStoreDb.collection(USERS_COLLECTION_REF).document(auth.currentUser?.uid!!)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.toObject(User::class.java)
                    trySend(ResultState.success(user!!))
                }
            }
            .addOnFailureListener {
                trySend(ResultState.error(it.message?: "Unknown error"))
            }

        awaitClose {
            close()
        }

    }

    override fun getChargingStationById(id: String): Flow<ResultState<ChargingStation>> = callbackFlow{
        fireStoreDb.collection(CHARGING_STATIONS_COLLECTION_REF).whereEqualTo("id", id)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        Log.i("ChargingStationRepositoryImpl", "Fetch station with id $id successful with name${document.getString("name")}")
                        val chargingStation = document.toObject(ChargingStation::class.java)
                        trySend(ResultState.success(chargingStation))
                    }
                }
            }
            .addOnFailureListener {
                trySend(ResultState.error(it.message?: "Unknown error"))
            }

        awaitClose {
            close()
        }

    }


    override fun getNearByStations(
        latitude: Double,
        longitude: Double
    ): Flow<ResultState<List<ChargingStation>>> = callbackFlow {

        trySend(ResultState.loading())
        val center = GeoLocation(latitude, longitude)
        //Radius of 15km
        val radiusInM = 15.0 * 1000.0

        val bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM)
        val tasks: MutableList<Task<QuerySnapshot>> = ArrayList()

        for (b in bounds) {
            val q = fireStoreDb.collection(CHARGING_STATIONS_COLLECTION_REF)
                .orderBy("geoHash")
                .startAt(b.startHash)
                .endAt(b.endHash)
            tasks.add(q.get())
        }

        Log.i("ChargingStationRepositoryImpl", "initial tasks size: ${tasks.size}")
        Tasks.whenAllComplete(tasks)
            .addOnCompleteListener {
                val matchingDocs: MutableList<ChargingStation> = ArrayList()
                for (task in tasks) {
                    val snap = task.result
                    for (doc in snap!!.documents) {
                        val lat = doc.getString("lat")!!.toDouble()
                        val lng = doc.getString("long")!!.toDouble()
                        val docLocation = GeoLocation(lat, lng)
                        val distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center)
                        if (distanceInM <= radiusInM) {
                            val chargingStation = doc.toObject(ChargingStation::class.java)
                            matchingDocs.add(chargingStation!!)
                        }
                    }
                }
                trySend(ResultState.success(matchingDocs))
            }
            .addOnFailureListener { exception->
                trySend(ResultState.error(exception.message?: "Unknown error occurred"))
            }

        awaitClose {
            close()
        }

    }

}
