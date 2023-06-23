package com.nganga.robert.chargelink.ui.viewmodels

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nganga.robert.chargelink.models.NewUser
import com.nganga.robert.chargelink.repository.AuthRepository
import com.nganga.robert.chargelink.screens.models.AddUserDetailsState
import com.nganga.robert.chargelink.screens.models.LoginState
import com.nganga.robert.chargelink.screens.models.SignUpState
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

    var addUserDetailsState by mutableStateOf(AddUserDetailsState())
        private set

    var loginState by mutableStateOf(LoginState())
        private set

    var signUpState by mutableStateOf(SignUpState())
        private set


    fun onSubmitUserDetailsClicked(
        name: String,
        phone:String,
        gender: String,
        dob: String
    ){
        val validate = validateUserDetails(name, phone, dob, gender)
        if (validate != null){
            addUserDetailsState = addUserDetailsState.copy(isError = true, errorMsg = validate)
        }else{
            addUserDetailsState = addUserDetailsState.copy(isError = false, errorMsg = "")
            val user = NewUser(name = name, phone = phone, email = "", gender = gender, dob = dob, imageUrl = "", cars = emptyList())
            addUserToDatabase(user)
        }

    }

    fun onLoginClicked(email: String, password: String){
        val validate = validateInputs(email, password)
        if (validate != null){
            loginState = loginState.copy(isError = true, errorMsg = validate)
        }else{
            loginState = loginState.copy(isError = false, errorMsg = "")
            login(email, password)
        }
    }

    fun onSignUpClicked(email: String, password: String){
        val validate = validateInputs(email, password)
        if (validate != null){
            signUpState = signUpState.copy(isError = true, errorMsg = validate)
        }else{
            signUpState = signUpState.copy(isError = false, errorMsg = "")
            createUser(email, password)
        }
    }

    private fun addUserToDatabase(user: NewUser) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            repository.addUserDetails(user).collect{ result->
                when(result.status){
                    ResultState.Status.SUCCESS->{
                        addUserDetailsState = addUserDetailsState.copy(
                            isUserAddedSuccessfully = true,
                            isLoading = false,
                            isError = false,
                            errorMsg = ""
                        )
                    }
                    ResultState.Status.LOADING->{
                        addUserDetailsState = addUserDetailsState.copy(isLoading = true)
                    }
                    ResultState.Status.ERROR->{
                        result.message?.let {
                            addUserDetailsState = addUserDetailsState.copy(
                                isError = true,
                                errorMsg = it,
                                isUserAddedSuccessfully = false,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
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

    private fun validateInputs(email: String, password: String): String?{
        return when{
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email address"
            password.isEmpty() -> "Password must not be empty"
            password.length < 6 -> "Password length must be greater than 6"
            else -> null
        }
    }

    private fun validateUserDetails(name: String, phone: String, dob: String, gender: String): String?{
        return when{
            name.isEmpty() -> "Name must not be empty"
            dob.isEmpty() -> "Date of birth must not be empty"
            gender.isEmpty() -> "Gender must not be empty"
            phone.length < 10 -> "Enter a valid phone number"
            else -> null
        }
    }


}