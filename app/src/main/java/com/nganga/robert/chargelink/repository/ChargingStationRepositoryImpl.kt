package com.nganga.robert.chargelink.repository

import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.nganga.robert.chargelink.models.NewChargingStation
import com.nganga.robert.chargelink.utils.Constants.CHARGING_STATIONS_COLLECTION_REF
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ChargingStationRepositoryImpl@Inject constructor(
    private val fireStoreDb: FirebaseFirestore): ChargingStationRepository {


    override suspend fun getNearByStations(
        latitude: Double,
        longitude: Double
    ): Flow<ResultState<List<NewChargingStation>>> = callbackFlow {

        trySend(ResultState.loading())

        val center = GeoLocation(latitude, longitude)
        val radiusInM = 50.0 * 1000.0

        val bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM)
        val tasks: MutableList<Task<QuerySnapshot>> = ArrayList()

        for (b in bounds) {
            val q = fireStoreDb.collection(CHARGING_STATIONS_COLLECTION_REF)
                .orderBy("geoHash")
                .startAt(b.startHash)
                .endAt(b.endHash)
            tasks.add(q.get())
        }

        Tasks.whenAllComplete(tasks)
            .addOnCompleteListener {
                val matchingDocs: MutableList<NewChargingStation> = ArrayList()
                for (task in tasks) {
                    val snap = task.result
                    for (doc in snap!!.documents) {
                        val lat = doc.getString("lat")!!.toDouble()
                        val lng = doc.getString("long")!!.toDouble()
                        val docLocation = GeoLocation(lat, lng)
                        val distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center)
                        if (distanceInM <= radiusInM) {
                            val chargingStation = doc.toObject(NewChargingStation::class.java)
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
