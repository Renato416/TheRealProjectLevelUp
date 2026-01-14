package com.example.therealprijectlevelup.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therealprijectlevelup.models.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    var user = mutableStateOf(User())
    var registerResult = mutableStateOf("")

    fun register() {
        viewModelScope.launch {
            registerResult.value = "Registrando..."

            delay(1500)

            val u = user.value
            registerResult.value = when {
                u.email.isBlank() || u.username.isBlank() ->
                    "Datos incompletos"

                u.password.length < 6 ->
                    "Contraseña muy corta"

                else -> "SUCCESS"
            }
        }
    }
}

