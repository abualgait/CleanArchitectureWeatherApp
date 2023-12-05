package com.weather.app.data.data_source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


object PREFS {

    val CITY_KEY = stringPreferencesKey("CITY_KEY")
    val DARK_MODE_KEY = booleanPreferencesKey("DARK_MODE")
}

@Singleton
class WeatherDataStore @Inject constructor(@ApplicationContext context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "WeatherAppDS")
    private val dataStore = context.dataStore

    //city prefs
    suspend fun setCityPrefs(location: String) {
        dataStore.edit { pref ->
            pref[PREFS.CITY_KEY] = location
        }
    }

    fun getCityPrefs(): Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { pref ->
            val location = pref[PREFS.CITY_KEY] ?: "Cairo, Egypt-127164"
            location
        }

    //dark mode prefs
    suspend fun setDarkThemePrefs(isDarkTheme: Boolean) {
        dataStore.edit { pref ->
            pref[PREFS.DARK_MODE_KEY] = isDarkTheme
        }
    }

    fun getDarkThemePrefs(): Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { pref ->
            val onboard = pref[PREFS.DARK_MODE_KEY] ?: false
            onboard
        }


}