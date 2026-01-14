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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.therealprijectlevelup.data.SettingsStore
import com.example.therealprijectlevelup.ui.theme.TheRealPrijectLevelUpTheme
import com.example.therealprijectlevelup.viewModels.*
import com.example.therealprijectlevelup.views.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val context = LocalContext.current
            val settingsStore = remember { SettingsStore(context) }
            val settingsViewModel = remember { SettingsViewModel(settingsStore) }

            val darkMode by settingsViewModel.isDarkMode.collectAsState()

            val homeViewModel: HomeViewModel = viewModel()
            val cartViewModel: CartViewModel = viewModel()
            val chatViewModel: ChatViewModel = viewModel()

            var currentScreen by rememberSaveable { mutableStateOf("home") }

            TheRealPrijectLevelUpTheme(darkTheme = darkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    when (currentScreen) {

                        "home" -> HomeView(
                            onNavigate = { currentScreen = it },
                            homeViewModel = homeViewModel,
                            settingsViewModel = settingsViewModel
                        )

                        "cart" -> CartView(
                            onNavigate = { currentScreen = it },
                            settingsViewModel = settingsViewModel,
                            cartViewModel = cartViewModel
                        )

                        "messages" -> ChatView(
                            onNavigate = { currentScreen = it },
                            settingsViewModel = settingsViewModel,
                            chatViewModel = chatViewModel
                        )

                        "profile" -> ProfileView(
                            onNavigate = { currentScreen = it },
                            viewModel = settingsViewModel
                        )
                    }
                }
            }
        }
    }
}
