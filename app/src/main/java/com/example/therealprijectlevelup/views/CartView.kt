package com.example.therealprijectlevelup.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// IMPORTANTE: VERIFICA QUE LA CARPETA SE LLAME 'viewmodels' EN MINÚSCULAS
import com.example.therealprijectlevelup.viewModels.SettingsViewModel

data class CartItemData(
    val id: Int, val name: String, val quantity: Int, val price: String
)

@Composable
fun CartView(onNavigate: (String) -> Unit, viewModel: SettingsViewModel) {
    val cartItems = listOf(
        CartItemData(1, "Audifonos gamer estilo creeper", 2, "41.980"),
        CartItemData(2, "Mesa gamer RGB", 1, "110.990")
    )

    Scaffold(
        topBar = { LevelUpHeader(title = "Level UP", viewModel = viewModel) },
        bottomBar = { LevelUpBottomNavigation(selectedTab = "cart", onTabSelected = onNavigate) },
        // CORRECCIÓN: USAR COLOR DEL TEMA
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(cartItems) { item -> CartItemRow(item) }
            }

            Spacer(modifier = Modifier.height(16.dp))
            DiscountCodeSection()
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total: $122.376",
                    fontSize = 32.sp,
                    // CORRECCIÓN: TEXTO REACTIVO AL MODO OSCURO
                    color = MaterialTheme.colorScheme.onBackground
                )

                Button(
                    onClick = { /* ACCIÓN */ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5877FF)),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text(text = "Pagar?", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun CartItemRow(item: CartItemData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        // CORRECCIÓN: BORDE REACTIVO
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(12.dp),
                // CORRECCIÓN: FONDO DE IMAGEN QUE NO DESLUMBRE EN MODO OSCURO
                color = MaterialTheme.colorScheme.surfaceVariant,
                border = BorderStroke(1.dp, Color.LightGray)
            ) { }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "${item.quantity} u.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "$${item.price}",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun DiscountCodeSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "codigo de descuento:", color = MaterialTheme.colorScheme.onSurface)
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "N1KOL4 T3SL4", color = MaterialTheme.colorScheme.onSurface)
                Text(text = "20% Descuento", color = Color(0xFF4CAF50)) // VERDE MANTENIDO
            }
        }
    }
}