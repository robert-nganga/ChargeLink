package com.nganga.robert.chargelink.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nganga.robert.chargelink.ui.screens.authentication.RegisterFormState
import com.nganga.robert.chargelink.ui.screens.authentication.TextFieldState

class AuthenticationViewModel: ViewModel() {

    var registerFormState = mutableStateOf(RegisterFormState(
        nameState = TextFieldState(),
        emailState = TextFieldState(),
        genderState = TextFieldState(),
        dobState = TextFieldState()
    ))
        private set


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