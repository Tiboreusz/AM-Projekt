package com.example.am_projekt.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val LANGUAGE_KEY = stringPreferencesKey("language")
fun getLanguagePreferenceFlow(context: Context): Flow<String> {
    return context.dataStore.data.map { it[LANGUAGE_KEY] ?: "pl" }
}


class DataStoreManager(private val context: Context) {

    companion object {
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }

    val darkModeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_MODE_KEY] ?: false
        }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }
}

object SettingsDataStore {
    private val Context.dataStore by preferencesDataStore(name = "settingss")
    val VOLUME_KEY = floatPreferencesKey("volume_level")

    suspend fun saveVolume(context: Context, volume: Float) {
        context.dataStore.edit { settingss ->
            settingss[VOLUME_KEY] = volume
        }
    }

    val volumeFlow: (Context) -> Flow<Float> = { context ->
        context.dataStore.data.map { it[VOLUME_KEY] ?: 1.0f }
    }
}

