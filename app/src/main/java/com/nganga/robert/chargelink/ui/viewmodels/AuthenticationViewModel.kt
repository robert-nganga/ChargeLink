package com.nganga.robert.chargelink.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nganga.robert.chargelink.repository.AuthRepository
import com.nganga.robert.chargelink.ui.screens.authentication.LoginState
import com.nganga.robert.chargelink.ui.screens.authentication.SignUpState
import com.nganga.robert.chargelink.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    val hasUser: Boolean
        get() = repository.hasUser()

    var loginState by mutableStateOf(LoginState())
        private set

    var signUpState by mutableStateOf(SignUpState())
        private set


    fun onLoginClicked(email: String, password: String){
        login(email, password)
    }

    fun onSignUpClicked(email: String, password: String){
        createUser(email, password)
    }

    private fun login(email: String, password: String) = viewModelScope.launch{
        withContext(Dispatchers.IO){
            repository.login(email, password).collect{ result->
                when(result.status){
                    ResultState.Status.SUCCESS->{
                        loginState = loginState.copy(
                            isLoginSuccessful = true,
                            isLoading = false,
                            isError = false,
                            errorMsg = ""
                        )
                    }
                    ResultState.Status.LOADING->{
                        loginState = loginState.copy(isLoading = true)
                    }
                    ResultState.Status.ERROR->{
                        result.message?.let {
                            loginState = loginState.copy(
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

    private fun createUser(email: String, password: String) = viewModelScope.launch{
        withContext(Dispatchers.IO){
            repository.createUser(email, password).collect{ result->
                when(result.status){
                    ResultState.Status.SUCCESS->{
                        signUpState = signUpState.copy(
                            isSignUpSuccessful = true,
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
                                isSignUpSuccessful = false,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


}