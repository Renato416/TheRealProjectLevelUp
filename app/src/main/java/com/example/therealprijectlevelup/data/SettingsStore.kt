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

// ⚠️ Nombre genérico y correcto del DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

class SettingsStore(private val context: Context) {

    companion object {
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_enabled")

        // PERFIL
        val STORED_USER_KEY = stringPreferencesKey("stored_username")
        val STORED_PASS_KEY = stringPreferencesKey("stored_password")
        val STORED_ADDRESS_KEY = stringPreferencesKey("stored_address")
        val STORED_PHONE_KEY = stringPreferencesKey("stored_phone")
        val STORED_BIRTH_KEY = stringPreferencesKey("stored_birth")
        val STORED_EMAIL_KEY = stringPreferencesKey("stored_email_text")

        // CARRITO
        val CART_ITEMS_KEY = stringPreferencesKey("cart_items_json")
    }

    private val gson = Gson()

    // =========================
    // 🛒 CARRITO
    // =========================
    val getCartItems: Flow<List<CartItem>> = context.dataStore.data.map { prefs ->
        val json = prefs[CART_ITEMS_KEY]
        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<List<CartItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    suspend fun saveCart(items: List<CartItem>) {
        context.dataStore.edit { prefs ->
            prefs[CART_ITEMS_KEY] = gson.toJson(items)
        }
    }

    // =========================
    // 🌙 DARK MODE
    // =========================
    val isDarkMode: Flow<Boolean> =
        context.dataStore.data.map { prefs ->
            prefs[DARK_MODE_KEY] ?: false
        }

    suspend fun saveDarkMode(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_MODE_KEY] = enabled
        }
    }

    // =========================
    // 👤 PERFIL / USUARIO
    // =========================
    val getStoredUser: Flow<String> =
        context.dataStore.data.map { it[STORED_USER_KEY] ?: "Usuario Invitado" }

    val getStoredPass: Flow<String> =
        context.dataStore.data.map { it[STORED_PASS_KEY] ?: "" }

    val getEmail: Flow<String> =
        context.dataStore.data.map { it[USER_EMAIL_KEY] ?: "" }

    val getUserProfile: Flow<UserProfile> =
        context.dataStore.data.map { prefs ->
            UserProfile(
                username = prefs[STORED_USER_KEY] ?: "Usuario Invitado",
                address = prefs[STORED_ADDRESS_KEY] ?: "",
                email = prefs[STORED_EMAIL_KEY] ?: "",
                phone = prefs[STORED_PHONE_KEY] ?: "",
                birthDate = prefs[STORED_BIRTH_KEY] ?: ""
            )
        }

    suspend fun saveFullProfile(
        username: String,
        pass: String,
        email: String,
        address: String,
        phone: String,
        birthDate: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[STORED_USER_KEY] = username
            prefs[STORED_PASS_KEY] = pass
            prefs[STORED_EMAIL_KEY] = email
            prefs[STORED_ADDRESS_KEY] = address
            prefs[STORED_PHONE_KEY] = phone
            prefs[STORED_BIRTH_KEY] = birthDate
        }
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_EMAIL_KEY] = email
        }
    }

    suspend fun saveCredentials(user: String, pass: String) {
        context.dataStore.edit { prefs ->
            prefs[STORED_USER_KEY] = user
            prefs[STORED_PASS_KEY] = pass
        }
    }
}
