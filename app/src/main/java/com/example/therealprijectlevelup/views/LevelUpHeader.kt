package com.example.therealprijectlevelup.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// IMPORTANTE: REEMPLAZA CON LA RUTA REAL DE TU VIEWMODEL
import com.example.therealprijectlevelup.viewModels.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelUpHeader(
    title: String,
    viewModel: SettingsViewModel // INYECTAMOS EL VIEWMODEL
) {
    // OBSERVAMOS EL ESTADO ACTUAL DEL MODO OSCURO
    val isDark by viewModel.isDarkMode.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF5877FF))
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                placeholder = { Text("Buscar...", fontSize = 14.sp) },
                trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )

            // BOTÓN CON FUNCIONALIDAD DE DATASTORE
            Button(
                onClick = {
                    // LLAMAMOS A LA LÓGICA DEL VIEWMODEL PARA INVERTIR EL TEMA
                    viewModel.toggleDarkMode(!isDark)
                },
                modifier = Modifier.height(50.dp),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    // EL COLOR DEL BOTÓN CAMBIA SEGÚN EL MODO PARA DAR FEEDBACK VISUAL
                    containerColor = if (isDark) Color.White else Color.Black
                )
            ) {
                Text(
                    text = if (isDark) "LUZ" else "MODO",
                    fontSize = 12.sp,
                    color = if (isDark) Color.Black else Color.White
                )
            }
        }
    }
}