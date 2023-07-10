package com.nganga.robert.chargelink.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.nganga.robert.chargelink.models.UserPreferences
import com.nganga.robert.chargelink.utils.Constants.IS_DARK_MODE_PREFERENCE_KEY
import com.nganga.robert.chargelink.utils.Constants.RADIUS_PREFERENCE_KEY
import com.nganga.robert.chargelink.utils.Constants.USE_SYSTEM_SETTINGS_PREFERENCE_KEY
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>) {

    private object PreferenceKeys{
        val RADIUS = floatPreferencesKey(RADIUS_PREFERENCE_KEY)
        val IS_DARK_MODE = booleanPreferencesKey(IS_DARK_MODE_PREFERENCE_KEY)
        val USE_SYSTEM_SETTINGS = booleanPreferencesKey(USE_SYSTEM_SETTINGS_PREFERENCE_KEY)
    }

    val userPreferences = dataStore.data
        .catch { exception->
            if(exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map { preferences->
            mapUserPreferences(preferences)
        }

    suspend fun switchToDarkMode(isDarkMode: Boolean) {
        dataStore.edit { preference->
            preference[PreferenceKeys.IS_DARK_MODE] = isDarkMode
        }
    }

    suspend fun changeRadius(radius: Float){
        dataStore.edit { preference->
            preference[PreferenceKeys.RADIUS] = radius
        }
    }

    suspend fun changeUseSystemSettings(useSystemSettings: Boolean){
        dataStore.edit { preference->
            preference[PreferenceKeys.USE_SYSTEM_SETTINGS] = useSystemSettings
        }
    }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val radius = preferences[PreferenceKeys.RADIUS] ?: 15.0f
        val isDarkMode = preferences[PreferenceKeys.IS_DARK_MODE] ?: false
        val useSystemSettings = preferences[PreferenceKeys.USE_SYSTEM_SETTINGS] ?: true
        return UserPreferences(radius, isDarkMode, useSystemSettings)
    }


}