package com.example.therealprijectlevelup.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.therealprijectlevelup.models.CartItem
import com.example.therealprijectlevelup.viewModels.UserProfile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserEmail")

class SettingsStore(private val context: Context) {

    companion object {
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_enabled")

        // CLAVES DE PERFIL
        val STORED_USER_KEY = stringPreferencesKey("stored_username")
        val STORED_PASS_KEY = stringPreferencesKey("stored_password")
        val STORED_ADDRESS_KEY = stringPreferencesKey("stored_address")
        val STORED_PHONE_KEY = stringPreferencesKey("stored_phone")
        val STORED_BIRTH_KEY = stringPreferencesKey("stored_birth")
        val STORED_EMAIL_KEY = stringPreferencesKey("stored_email_text")

        // NUEVA CLAVE PARA EL CARRITO (Guardado como JSON String)
        val CART_ITEMS_KEY = stringPreferencesKey("cart_items_json")
    }

    private val gson = Gson()

    // --- LECTURA DEL CARRITO ---
    val getCartItems: Flow<List<CartItem>> = context.dataStore.data.map { preferences ->
        val jsonString = preferences[CART_ITEMS_KEY] ?: ""
        if (jsonString.isNotEmpty()) {
            val type = object : TypeToken<List<CartItem>>() {}.type
            gson.fromJson(jsonString, type)
        } else {
            emptyList()
        }
    }

    // --- GUARDADO DEL CARRITO ---
    suspend fun saveCart(items: List<CartItem>) {
        val jsonString = gson.toJson(items)
        context.dataStore.edit { preferences ->
            preferences[CART_ITEMS_KEY] = jsonString
        }
    }

    // ... (El resto de tus funciones de Usuario/Perfil se mantienen igual) ...
    val getStoredUser: Flow<String> = context.dataStore.data.map { preferences -> preferences[STORED_USER_KEY] ?: "Renato416" }
    val getStoredPass: Flow<String> = context.dataStore.data.map { preferences -> preferences[STORED_PASS_KEY] ?: "123456" }
    val getEmail: Flow<String> = context.dataStore.data.map { preferences -> preferences[USER_EMAIL_KEY] ?: "" }
    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences -> preferences[DARK_MODE_KEY] ?: false }

    val getUserProfile: Flow<UserProfile> = context.dataStore.data.map { preferences ->
        UserProfile(
            username = preferences[STORED_USER_KEY] ?: "Usuario Invitado",
            address = preferences[STORED_ADDRESS_KEY] ?: "Sin dirección",
            email = preferences[STORED_EMAIL_KEY] ?: "Sin email",
            phone = preferences[STORED_PHONE_KEY] ?: "Sin teléfono",
            birthDate = preferences[STORED_BIRTH_KEY] ?: "Sin fecha"
        )
    }

    suspend fun saveFullProfile(username: String, pass: String, email: String, address: String, phone: String, birthDate: String) {
        context.dataStore.edit { preferences ->
            preferences[STORED_USER_KEY] = username
            preferences[STORED_PASS_KEY] = pass
            preferences[STORED_EMAIL_KEY] = email
            preferences[STORED_ADDRESS_KEY] = address
            preferences[STORED_PHONE_KEY] = phone
            preferences[STORED_BIRTH_KEY] = birthDate
        }
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { preferences -> preferences[USER_EMAIL_KEY] = email }
    }

    suspend fun saveDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences -> preferences[DARK_MODE_KEY] = enabled }
    }

    suspend fun saveCredentials(user: String, pass: String) {
        context.dataStore.edit { preferences ->
            preferences[STORED_USER_KEY] = user
            preferences[STORED_PASS_KEY] = pass
        }
    }
}