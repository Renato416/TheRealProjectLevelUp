package com.example.therealprijectlevelup.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therealprijectlevelup.data.SettingsStore
import com.example.therealprijectlevelup.models.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel(private val settingsStore: SettingsStore) : ViewModel() {

    var user = mutableStateOf(User())

    // VARIABLE SOLO PARA LA UI (NO VA A LA BD)
    var confirmPassword = mutableStateOf("")

    var registerResult = mutableStateOf("")

    fun register() {
        viewModelScope.launch {
            registerResult.value = "Registrando..."
            delay(1500)

            val u = user.value
            val confirm = confirmPassword.value

            when {
                u.email.isBlank() || u.username.isBlank() ->
                    registerResult.value = "Datos incompletos"

                u.password.length < 6 ->
                    registerResult.value = "La contraseña debe tener al menos 6 caracteres"

                // NUEVA VALIDACIÓN: COMPARAR LAS DOS CONTRASEÑAS
                u.password != confirm ->
                    registerResult.value = "Las contraseñas no coinciden"

                else -> {
                    settingsStore.saveCredentials(u.username, u.password)
                    settingsStore.saveEmail(u.username)
                    registerResult.value = "SUCCESS"
                }
            }
        }
    }
}