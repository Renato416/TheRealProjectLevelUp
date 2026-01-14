package com.example.therealprijectlevelup.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserEmail")

class SettingsStore(private val context: Context) {

    companion object {
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_enabled")
    }

    // FLUJO DE DATOS PARA EL EMAIL (POR DEFECTO VACÍO)
    val getEmail: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_EMAIL_KEY] ?: ""
    }

    // FLUJO DE DATOS PARA EL MODO OSCURO (POR DEFECTO FALSO)
    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY] ?: false
    }

    // FUNCIONES SUSPENDIDAS PARA GUARDAR (DEBEN LLAMARSE DESDE UN COROUTINESCOPE)
    suspend fun saveEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = email
        }
    }

    suspend fun saveDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }
}