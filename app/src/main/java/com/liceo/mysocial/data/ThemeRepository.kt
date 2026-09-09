package com.liceo.mysocial.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ThemeRepository(private val context: Context) {
    private object Keys {
        // TODO 7a: create a Boolean key whose saved name is "dark_theme"
        val DARK_THEME = booleanPreferencesKey("dark_theme")
    }

    // TODO 7b: read the saved value as a Flow, using false when nothing is saved yet
    val isDarkTheme: Flow<Boolean> = context.settingsDataStore.data
        .catch { e -> if (e is IOException) emit(emptyPreferences()) else throw e }
        .map { prefs -> prefs[Keys.DARK_THEME] ?: false }

    // TODO 8: save the new value
    suspend fun setDarkTheme(enabled: Boolean) {
        context.settingsDataStore.edit { prefs ->
            prefs[Keys.DARK_THEME] = enabled
        }
    }
}
