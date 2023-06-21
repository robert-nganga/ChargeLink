package com.nganga.robert.chargelink.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.nganga.robert.chargelink.repository.AuthRepository
import com.nganga.robert.chargelink.ui.screens.authentication.EnterNumberState
import com.nganga.robert.chargelink.ui.screens.authentication.OtpVerificationState
import com.nganga.robert.chargelink.ui.screens.authentication.RegisterFormState
import com.nganga.robert.chargelink.ui.screens.authentication.TextFieldState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthenticationViewModel(
    private val repository: AuthRepository = AuthRepository()
): ViewModel() {

    val hasUser: Boolean
        get() = repository.hasUser()

    var enterNumberState by mutableStateOf(EnterNumberState())
        private set

    var otpVerificationState by mutableStateOf(OtpVerificationState())
        private set

    var registerFormState = mutableStateOf(RegisterFormState(
        nameState = TextFieldState(),
        emailState = TextFieldState(),
        genderState = TextFieldState(),
        dobState = TextFieldState()
    ))
        private set

    private var verificationId: String? = null


    fun onSubmitNumberForVerification() = viewModelScope.launch{
        Log.d("FirebaseAuth", "sent verification code")
        withContext(Dispatchers.IO){
            val number  = "+254${enterNumberState.number}"
            repository.sendVerificationCode(
                number,
                onVerificationComplete = { credential->
                    otpVerificationState = otpVerificationState.copy(isLoading = true)
                    signInUserWithCredential(credential)
                },
                onVerificationFailed = { e->
                    otpVerificationState = otpVerificationState.copy(
                        isError = true,
                        isLoading = false,
                        errorMsg = e.message ?: "Unknown error"
                    )
                },
                onCodeSent = {
                    verificationId = it
                }
            )
        }
    }

    private fun signInUserWithCredential(credential: PhoneAuthCredential)= viewModelScope.launch{
        withContext(Dispatchers.IO){
            repository.signInWithPhoneAuthCredential(credential){ isSuccessful->
                otpVerificationState = if (isSuccessful){
                    otpVerificationState.copy(isSignInSuccessful = true, isLoading = false)
                }else{
                    otpVerificationState.copy(isSignInSuccessful = false, isLoading = false)
                }
            }
        }
    }

    fun onOtpCodeChange(code: String){
        otpVerificationState = otpVerificationState.copy(otpCode = code)
    }

    fun onSubmitOtpCode(){
        verificationId?.let {
            val credential = PhoneAuthProvider.getCredential(it, otpVerificationState.otpCode)
            otpVerificationState = otpVerificationState.copy(isLoading = true)
            signInUserWithCredential(credential)
        }

    }

    fun onNumberChanged(number: String){
        enterNumberState = enterNumberState.copy(number = number)
    }

    fun onNameChange(name: String){
        val newState = registerFormState.value.nameState.copy(text = name)
        registerFormState.value = registerFormState.value.copy(nameState = newState)
    }

    fun onEmailChange(email: String){
        val newState = registerFormState.value.emailState.copy(text = email)
        registerFormState.value = registerFormState.value.copy(emailState = newState)
    }

    fun onGenderChange(gender: String){
        val newState = registerFormState.value.genderState.copy(text = gender)
        registerFormState.value = registerFormState.value.copy(genderState = newState)
    }

    fun onDobChange(dob: String){
        val newState = registerFormState.value.dobState.copy(text = dob)
        registerFormState.value = registerFormState.value.copy(dobState = newState)
    }
}