package com.nganga.robert.chargelink.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.nganga.robert.chargelink.models.Car
import com.nganga.robert.chargelink.models.NewUser
import com.nganga.robert.chargelink.utils.Constants.USERS_COLLECTION_REF
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val fireStoreDb: FirebaseFirestore,
    private val auth: FirebaseAuth): AuthRepository {


    override fun hasUser(): Boolean = auth.currentUser != null


    override fun logout() {
        auth.signOut()
    }


    override fun addUserDetails(user: NewUser): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.loading())
            fireStoreDb.collection(USERS_COLLECTION_REF).document(auth.currentUser?.uid!!)
                .set(user.copy(email = auth.currentUser?.email!!).toMap())
                .addOnSuccessListener {
                    trySend(ResultState.success("Added successfully"))
                }
                .addOnFailureListener { exception ->
                    trySend(ResultState.error(exception.message?: "Unknown error"))
                }


        awaitClose {
            close()
        }
    }

    override fun addUserCarDetails(car: Car): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.loading())
        fireStoreDb.collection(USERS_COLLECTION_REF).document(auth.currentUser!!.uid)
            .update("cars", FieldValue.arrayUnion(car.toMap()))
            .addOnCompleteListener {
                trySend(ResultState.success("Car details added successfully"))
            }
            .addOnFailureListener { exception ->
                trySend(ResultState.error(exception.message?: "Unknown error"))
            }

        awaitClose {
            close()
        }
    }

    override fun createUser(
        email: String,
        password: String
    ): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.loading())
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(ResultState.success("user created successfully"))
                }
            }
            .addOnFailureListener { exception->
                trySend(ResultState.error(exception.message?: "Unknown error"))
            }
        awaitClose {
            close()
        }
    }

    override fun login(
        email: String,
        password: String
    ): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.loading())
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(ResultState.success("Login successfully"))
                }
            }
            .addOnFailureListener { exception->
                trySend(ResultState.error(exception.message?: "Unknown error"))
            }
        awaitClose {
            close()
        }
    }

}