package com.example.therealprijectlevelup.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therealprijectlevelup.data.SettingsStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsStore: SettingsStore) : ViewModel() {

    // TRANSFORMAMOS EL FLOW EN STATEFLOW PARA QUE LA UI LO LEA FÁCILMENTE
    val isDarkMode: StateFlow<Boolean> = settingsStore.isDarkMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val userEmail: StateFlow<String> = settingsStore.getEmail
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    // FUNCIONES PARA MODIFICAR LOS DATOS
    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            settingsStore.saveDarkMode(enabled)
        }
    }

    fun updateEmail(email: String) {
        viewModelScope.launch {
            settingsStore.saveEmail(email)
        }
    }
}