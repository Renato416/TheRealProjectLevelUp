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
    var confirmPassword = mutableStateOf("")
    var registerResult = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun clean() {
        user.value = User()
        confirmPassword.value = ""
        registerResult.value = ""
        isLoading.value = false
    }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            registerResult.value = ""

            delay(1500)

            val u = user.value
            val confirm = confirmPassword.value

            when {
                u.email.isBlank() || u.username.isBlank() ->
                    registerResult.value = "Datos incompletos"
                u.password.length < 6 ->
                    registerResult.value = "La contraseña debe tener al menos 6 caracteres"
                u.password != confirm ->
                    registerResult.value = "Las contraseñas no coinciden"
                else -> {
                    settingsStore.saveFullProfile(
                        username = u.username,
                        pass = u.password,
                        email = u.email,
                        address = u.address,
                        phone = u.phone,
                        birthDate = u.birthDate
                    )

                    onSuccess()
                    clean()
                }
            }
            isLoading.value = false
        }
    }
}