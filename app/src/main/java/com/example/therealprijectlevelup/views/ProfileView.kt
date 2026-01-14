package com.example.therealprijectlevelup.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.therealprijectlevelup.viewModels.SettingsViewModel

@Composable
fun ProfileView(
    onNavigate: (String) -> Unit,
    viewModel: SettingsViewModel
) {
    Scaffold(
        topBar = { LevelUpHeader("Level UP", viewModel) },
        bottomBar = { LevelUpBottomNavigation("profile", onNavigate) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Perfil de usuario")
        }
    }
}
