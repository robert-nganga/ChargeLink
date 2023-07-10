package com.nganga.robert.chargelink.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.nganga.robert.chargelink.models.UserPreferences
import com.nganga.robert.chargelink.utils.Constants.APP_THEME_PREFERENCE
import com.nganga.robert.chargelink.utils.Constants.RADIUS_PREFERENCE_KEY
import com.nganga.robert.chargelink.utils.ThemeSelection
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>) {

    private object PreferenceKeys{
        val RADIUS = floatPreferencesKey(RADIUS_PREFERENCE_KEY)
        val APP_THEME = stringPreferencesKey(APP_THEME_PREFERENCE)
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

    suspend fun changeAppTheme(appTheme: String) {
        dataStore.edit { preference->
            preference[PreferenceKeys.APP_THEME] = appTheme
        }
    }

    suspend fun changeRadius(radius: Float){
        dataStore.edit { preference->
            preference[PreferenceKeys.RADIUS] = radius
        }
    }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val radius = preferences[PreferenceKeys.RADIUS] ?: 15.0f
        val appTheme = preferences[PreferenceKeys.APP_THEME] ?: ThemeSelection.USE_SYSTEM_SETTINGS
        return UserPreferences(radius, appTheme)
    }


}