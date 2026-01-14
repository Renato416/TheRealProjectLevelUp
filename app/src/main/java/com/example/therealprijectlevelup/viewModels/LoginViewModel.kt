package com.example.therealprijectlevelup.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var username = mutableStateOf("")
    var password = mutableStateOf("")

    var loginResult = mutableStateOf("")

    fun login() {
        viewModelScope.launch {
            loginResult.value = "Cargando..."

            delay(1200) // Simula backend

            loginResult.value = when {
                username.value.isBlank() || password.value.isBlank() ->
                    "Campos vacíos"

                username.value == "Renato416" && password.value == "123456" ->
                    "SUCCESS"

                else -> "Credenciales incorrectas"
            }
        }
    }
}

