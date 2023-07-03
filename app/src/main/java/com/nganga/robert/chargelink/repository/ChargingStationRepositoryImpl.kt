package com.nganga.robert.chargelink.repository

import android.util.Log
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.Charger
import com.nganga.robert.chargelink.models.NewChargingStation
import com.nganga.robert.chargelink.models.NewUser
import com.nganga.robert.chargelink.models.Review
import com.nganga.robert.chargelink.utils.Constants.CHARGING_STATIONS_COLLECTION_REF
import com.nganga.robert.chargelink.utils.Constants.USERS_COLLECTION_REF
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ChargingStationRepositoryImpl@Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStoreDb: FirebaseFirestore): ChargingStationRepository {

    val chargers = listOf(
        Charger(plug = "CCS 1 DC", power = "360kW", image = R.drawable.ic_ev_plug_ccs2, isAvailable = true
        ),
        Charger(plug = "CCS 2 DC", power = "360kW", image = R.drawable.ic_ev_plug_ccs2_combo, isAvailable = true
        ),
        Charger(plug = "Mennekes (Type 2) AC", power = "22kW", image = R.drawable.ic_ev_plug_iec_mennekes_t2, isAvailable = true
        ),
        Charger(plug = "J1772 (Type 1) AC", power = "19.2kW", image = R.drawable.ic_ev_plug_j1772_t1, isAvailable = true
        ),
        Charger(plug = "Tesla NACS AC/DC", power = "250kW", image = R.drawable.ic_ev_plug_tesla, isAvailable = true
        )
    )

    init {
        addChargers(chargers)
    }

    override fun addChargers(chargers: List<Charger>) {
        chargers.forEach {
            addCharger(it)
        }
    }

    private fun addCharger(charger: Charger){
        val id = UUID.randomUUID().toString()
        fireStoreDb.collection("chargers").document(id)
            .set(charger.copy(id = id).toMap())
    }


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


    override fun getCurrentUser(): Flow<ResultState<NewUser>> = callbackFlow{
        fireStoreDb.collection(USERS_COLLECTION_REF).document(auth.currentUser?.uid!!)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.toObject(NewUser::class.java)
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

    override fun getChargingStationById(id: String): Flow<ResultState<NewChargingStation>> = callbackFlow{
        fireStoreDb.collection(CHARGING_STATIONS_COLLECTION_REF).whereEqualTo("id", id)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        Log.i("ChargingStationRepositoryImpl", "Fetch station with id $id successful with name${document.getString("name")}")
                        val chargingStation = document.toObject(NewChargingStation::class.java)
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
    ): Flow<ResultState<List<NewChargingStation>>> = callbackFlow {

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
