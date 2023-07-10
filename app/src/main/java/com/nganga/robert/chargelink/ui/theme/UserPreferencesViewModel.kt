package com.nganga.robert.chargelink.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nganga.robert.chargelink.data.preferences.UserPreferencesRepository
import com.nganga.robert.chargelink.utils.ThemeSelection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserPreferencesViewModel@Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel(){

    val userPreferences = userPreferencesRepository.userPreferences.asLiveData()

    fun themeSelectionChange(appTheme: String) = viewModelScope.launch(Dispatchers.IO){
        when(appTheme){
            ThemeSelection.DARK_MODE -> {
                userPreferencesRepository.changeAppTheme(ThemeSelection.DARK_MODE)
            }
            ThemeSelection.LIGHT_MODE -> {
                userPreferencesRepository.changeAppTheme(ThemeSelection.LIGHT_MODE)
            }
            ThemeSelection.USE_SYSTEM_SETTINGS -> {
                userPreferencesRepository.changeAppTheme(ThemeSelection.USE_SYSTEM_SETTINGS)
            }
        }
    }


    fun changeRadius(radius: Float) = viewModelScope.launch(Dispatchers.IO){
        userPreferencesRepository.changeRadius(radius)
    }
}