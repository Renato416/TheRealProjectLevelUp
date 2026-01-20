package com.example.therealprijectlevelup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.therealprijectlevelup.data.SettingsStore
import com.example.therealprijectlevelup.navigations.NavManager // IMPORTAR EL NUEVO ARCHIVO
import com.example.therealprijectlevelup.ui.theme.TheRealPrijectLevelUpTheme
import com.example.therealprijectlevelup.viewModels.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current

            // 1. INSTANCIAS DE VIEWMODELS (Igual que antes)
            val settingsStore = remember { SettingsStore(context) }
            val settingsViewModel = remember { SettingsViewModel(settingsStore) }
            val loginViewModel = remember { LoginViewModel(settingsStore) }
            val registerViewModel = remember { RegisterViewModel(settingsStore) }
            val homeViewModel = remember { HomeViewModel() }
            val cartViewModel = remember { CartViewModel() }
            val chatViewModel = remember { ChatViewModel() }
            val profileViewModel = remember { ProfileViewModel(settingsStore) }

            val darkModeActive by settingsViewModel.isDarkMode.collectAsState()

            TheRealPrijectLevelUpTheme(darkTheme = darkModeActive) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 2. AQUÍ ESTÁ EL CAMBIO:
                    // Borramos el "var currentScreen" y el "when" gigante.
                    // Llamamos al NavManager pasándole todos los ViewModels.

                    NavManager(
                        settingsViewModel = settingsViewModel,
                        loginViewModel = loginViewModel,
                        registerViewModel = registerViewModel,
                        homeViewModel = homeViewModel,
                        cartViewModel = cartViewModel,
                        chatViewModel = chatViewModel,
                        profileViewModel = profileViewModel
                    )
                }
            }
        }
    }
}