package com.example.therealprijectlevelup.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.therealprijectlevelup.viewModels.SettingsViewModel

@Composable
fun ProfileView(onNavigate: (String) -> Unit, viewModel: SettingsViewModel) { // SE AGREGÓ EL PARÁMETRO DE NAVEGACIÓN
    Scaffold(
        topBar = { LevelUpHeader(title = "Level UP", viewModel = viewModel) },
        bottomBar = {
            LevelUpBottomNavigation(
                selectedTab = "profile",
                onTabSelected = onNavigate // PASAMOS LA FUNCIÓN AL COMPONENTE REUTILIZABLE
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileContentCard()
        }
    }
}

@Composable
fun ProfileContentCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(110.dp),
                shape = CircleShape,
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(3.dp, Color(0xFF455A64))
            ) { }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Text(text = "Renato416", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            InfoField(label = "Dirección", value = "Casablanca, Valparaíso", icon = Icons.Default.LocationOn)
            InfoField(label = "Correo electrónico", value = "re.rojasc@duocuc.cl")
            InfoField(label = "Número de teléfono", value = "9 3094 5360")
            InfoField(label = "Edad", value = "19 - 07 - 2005")

            Text(
                text = "¿Necesito ayuda?",
                color = Color(0xFF5877FF),
                modifier = Modifier.align(Alignment.End).padding(top = 16.dp),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun InfoField(label: String, value: String, icon: ImageVector? = null) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(start = 4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            trailingIcon = icon?.let { { Icon(it, contentDescription = null, tint = Color.Black) } },
            textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold)
        )
    }
}