package com.nganga.robert.chargelink.repository

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class AuthRepository {
    private val tag = "FirebaseAuth"
    private val auth = FirebaseAuth.getInstance()

    private var storedVerificationId: String? = null
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken



    fun hasUser(): Boolean = auth.currentUser != null



    suspend fun sendVerificationCode(
        number: String,
        onVerificationComplete: (PhoneAuthCredential)-> Unit,
        onVerificationFailed: (FirebaseException) -> Unit,
        onCodeSent: (String) -> Unit,
    ){

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    onVerificationComplete.invoke(credential)
                    Log.d(tag, "onVerificationCompleted:$credential")
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onVerificationFailed.invoke(e)
                    Log.w(tag, "onVerificationFailed", e)
                    when (e) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            // Invalid request
                        }
                        is FirebaseTooManyRequestsException -> {
                            // The SMS quota for the project has been exceeded
                        }
                        is FirebaseAuthMissingActivityForRecaptchaException -> {
                            // reCAPTCHA verification attempted with null Activity
                        }
                    }
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken,
                ) {
                    onCodeSent.invoke(verificationId)
                    Log.d(tag, "onCodeSent:$verificationId")
                    storedVerificationId = verificationId
                    resendToken = token
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    suspend fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, onComplete: (Boolean)->Unit) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            }.await()
    }


}