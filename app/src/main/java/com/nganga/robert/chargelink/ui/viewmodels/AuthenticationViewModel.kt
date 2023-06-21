package com.nganga.robert.chargelink.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nganga.robert.chargelink.repository.AuthRepository
import com.nganga.robert.chargelink.repository.AuthRepositoryImpl
import com.nganga.robert.chargelink.ui.screens.authentication.LoginState
import com.nganga.robert.chargelink.ui.screens.authentication.SignUpState
import com.nganga.robert.chargelink.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@HiltViewModel
class AuthenticationViewModel(
    private val repository: AuthRepository
): ViewModel() {

    var loginState by mutableStateOf(LoginState())
        private set

    var signUpState by mutableStateOf(SignUpState())
        private set


    fun onLoginClicked(email: String, password: String){

    }

    fun onSignUpClicked(email: String, password: String){

    }

    fun createUser(email: String, password: String) = viewModelScope.launch{
        withContext(Dispatchers.IO){
            repository.createUser(email, password).collect{ result->
                when(result.status){
                    ResultState.Status.SUCCESS->{
                        signUpState = signUpState.copy(
                            isLoginSuccessful = true,
                            isLoading = false,
                            isError = false,
                            errorMsg = ""
                        )
                    }
                    ResultState.Status.LOADING->{
                        signUpState = signUpState.copy(isLoading = true)
                    }
                    ResultState.Status.ERROR->{
                        result.message?.let {
                            signUpState = signUpState.copy(
                                isError = true,
                                errorMsg = it,
                                isLoginSuccessful = false,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


}