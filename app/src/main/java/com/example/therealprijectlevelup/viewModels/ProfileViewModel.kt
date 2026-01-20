package com.example.therealprijectlevelup.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therealprijectlevelup.data.SettingsStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// DEFINICIÓN DEL MODELO DE PERFIL
data class UserProfile(
    val username: String,
    val address: String,
    val email: String,
    val phone: String,
    val birthDate: String
)

// AHORA RECIBE EL STORE COMO PARÁMETRO
class ProfileViewModel(private val settingsStore: SettingsStore) : ViewModel() {

    private val _userProfile = MutableStateFlow(
        UserProfile("", "", "", "", "")
    )
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    init {
        // CARGAMOS LOS DATOS REALES AL INICIAR
        viewModelScope.launch {
            settingsStore.getUserProfile.collect { profile ->
                _userProfile.value = profile
            }
        }
    }
}