package com.example.therealprijectlevelup.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.therealprijectlevelup.viewModels.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Instancia única del DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserEmail")

class SettingsStore(private val context: Context) {

    companion object {
        // CLAVES DE ALMACENAMIENTO
        val USER_EMAIL_KEY = stringPreferencesKey("user_email") // Sesión activa
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_enabled")

        // CLAVES DEL PERFIL DE USUARIO
        val STORED_USER_KEY = stringPreferencesKey("stored_username")
        val STORED_PASS_KEY = stringPreferencesKey("stored_password")
        val STORED_ADDRESS_KEY = stringPreferencesKey("stored_address")
        val STORED_PHONE_KEY = stringPreferencesKey("stored_phone")
        val STORED_BIRTH_KEY = stringPreferencesKey("stored_birth")
        val STORED_EMAIL_KEY = stringPreferencesKey("stored_email_text")
    }

    // --------------------------------------------------------
    // 1. ESTAS SON LAS VARIABLES QUE TE FALTAN PARA EL LOGIN
    // --------------------------------------------------------

    // Obtener solo el usuario (para LoginViewModel)
    val getStoredUser: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[STORED_USER_KEY] ?: "Renato416" // Valor por defecto para pruebas
    }

    // Obtener solo la contraseña (para LoginViewModel)
    val getStoredPass: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[STORED_PASS_KEY] ?: "123456"
    }

    // Obtener email de sesión activa (para saber si entrar directo al Home)
    val getEmail: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_EMAIL_KEY] ?: ""
    }

    // Obtener Modo Oscuro
    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY] ?: false
    }

    // --------------------------------------------------------
    // 2. VARIABLE PARA EL PERFIL (ProfileViewModel)
    // --------------------------------------------------------
    val getUserProfile: Flow<UserProfile> = context.dataStore.data.map { preferences ->
        UserProfile(
            username = preferences[STORED_USER_KEY] ?: "Usuario Invitado",
            address = preferences[STORED_ADDRESS_KEY] ?: "Sin dirección",
            email = preferences[STORED_EMAIL_KEY] ?: "Sin email",
            phone = preferences[STORED_PHONE_KEY] ?: "Sin teléfono",
            birthDate = preferences[STORED_BIRTH_KEY] ?: "Sin fecha"
        )
    }

    // --------------------------------------------------------
    // 3. FUNCIONES DE GUARDADO
    // --------------------------------------------------------

    // Guardar TODOS los datos del registro
    suspend fun saveFullProfile(
        username: String,
        pass: String,
        email: String,
        address: String,
        phone: String,
        birthDate: String
    ) {
        context.dataStore.edit { preferences ->
            preferences[STORED_USER_KEY] = username
            preferences[STORED_PASS_KEY] = pass
            preferences[STORED_EMAIL_KEY] = email
            preferences[STORED_ADDRESS_KEY] = address
            preferences[STORED_PHONE_KEY] = phone
            preferences[STORED_BIRTH_KEY] = birthDate
        }
    }

    // Guardar solo la sesión activa (cuando haces login exitoso)
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

    // Guardar credenciales básicas (si se usara en otro lado)
    suspend fun saveCredentials(user: String, pass: String) {
        context.dataStore.edit { preferences ->
            preferences[STORED_USER_KEY] = user
            preferences[STORED_PASS_KEY] = pass
        }
    }
}