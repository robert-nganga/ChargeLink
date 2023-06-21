package com.nganga.robert.chargelink.repository

import com.google.firebase.auth.FirebaseAuth
import com.nganga.robert.chargelink.utils.ResultState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth): AuthRepository {

    override fun hasUser(): Boolean = auth.currentUser != null

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
                trySend(ResultState.error(exception.message))
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
                trySend(ResultState.error(exception.message))
            }
        awaitClose {
            close()
        }
    }

}