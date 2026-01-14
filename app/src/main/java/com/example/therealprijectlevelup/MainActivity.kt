package com.example.therealprijectlevelup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.therealprijectlevelup.data.SettingsStore
import com.example.therealprijectlevelup.ui.theme.TheRealPrijectLevelUpTheme
import com.example.therealprijectlevelup.views.CartView
import com.example.therealprijectlevelup.views.HomeView
import com.example.therealprijectlevelup.views.ProfileView
import com.example.therealprijectlevelup.views.CustomerServiceView
// IMPORTANTE: ASEGÚRATE DE IMPORTAR TU VIEWMODEL
import com.example.therealprijectlevelup.viewModels.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current

            // 1. INICIALIZAMOS EL STORE Y EL VIEWMODEL
            // USAMOS REMEMBER PARA QUE EL VIEWMODEL PERSISTA DURANTE RECOMPOSICIONES
            val settingsStore = remember { SettingsStore(context) }
            val settingsViewModel = remember { SettingsViewModel(settingsStore) }

            // 2. OBSERVAMOS EL MODO OSCURO DESDE EL VIEWMODEL (ESTO ES MÁS LIMPIO)
            val darkModeActive by settingsViewModel.isDarkMode.collectAsState()

            TheRealPrijectLevelUpTheme(darkTheme = darkModeActive) {
                var currentScreen by rememberSaveable { mutableStateOf("home") }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 3. PASAMOS EL VIEWMODEL A CADA VISTA PARA SOLUCIONAR EL ERROR
                    when (currentScreen) {
                        "home" -> HomeView(
                            onNavigate = { currentScreen = it },
                            viewModel = settingsViewModel
                        )
                        "profile" -> ProfileView(
                            onNavigate = { currentScreen = it },
                            viewModel = settingsViewModel
                        )
                        "cart" -> CartView(
                            onNavigate = { currentScreen = it },
                            viewModel = settingsViewModel
                        )
                        "messages" -> CustomerServiceView(
                            onNavigate = { currentScreen = it },
                            viewModel = settingsViewModel
                        )
                    }
                }
            }
        }
    }
}