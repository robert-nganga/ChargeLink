package com.nganga.robert.chargelink.ui.theme

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nganga.robert.chargelink.data.preferences.UserPreferencesRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltAndroidApp
class UserPreferencesViewModel@Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel(){

    val userPreferences = userPreferencesRepository.userPreferences.asLiveData()

    fun switchToDarkMode(isDarkMode: Boolean) = viewModelScope.launch(Dispatchers.IO){
        userPreferencesRepository.switchToDarkMode(isDarkMode)
    }

    fun changeRadius(radius: Float) = viewModelScope.launch(Dispatchers.IO){
        userPreferencesRepository.changeRadius(radius)
    }

    fun changeUseSystemSettings(useSystemSettings: Boolean) = viewModelScope.launch(Dispatchers.IO){
        userPreferencesRepository.changeUseSystemSettings(useSystemSettings)
    }
}