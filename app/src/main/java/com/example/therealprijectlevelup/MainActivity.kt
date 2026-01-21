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
import com.example.therealprijectlevelup.navigations.NavManager
import com.example.therealprijectlevelup.ui.theme.TheRealPrijectLevelUpTheme
import com.example.therealprijectlevelup.viewModels.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current

            // Stores (esto está bien)
            val settingsStore = remember { SettingsStore(context) }

            // ViewModels SIN Hilt (correcto si NO usan repository)
            val settingsViewModel = remember { SettingsViewModel(settingsStore) }
            val loginViewModel = remember { LoginViewModel(settingsStore) }
            val registerViewModel = remember { RegisterViewModel(settingsStore) }
            val cartViewModel = remember { CartViewModel(settingsStore) }
            val chatViewModel = remember { ChatViewModel() }
            val profileViewModel = remember { ProfileViewModel(settingsStore) }

            // 🚨 HomeViewModel CON HILT
            val homeViewModel: HomeViewModel = androidx.hilt.navigation.compose.hiltViewModel()

            val darkModeActive by settingsViewModel.isDarkMode.collectAsState()

            TheRealPrijectLevelUpTheme(darkTheme = darkModeActive) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
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
