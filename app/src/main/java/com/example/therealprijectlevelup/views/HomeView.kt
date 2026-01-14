package com.example.therealprijectlevelup.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.therealprijectlevelup.viewModels.SettingsViewModel

// 1. SOLUCIÓN AL ERROR 'Unresolved reference ProductPlaceholder'
// DEFINIMOS EL MODELO DE DATOS (REPRESENTA EL "MODEL" EN MVVM)
data class ProductPlaceholder(
    val id: Int,
    val name: String,
    val price: String
)

@Composable
fun HomeView(onNavigate: (String) -> Unit, viewModel: SettingsViewModel) {
    // AL DEFINIR LA DATA CLASS ARRIBA, KOTLIN YA SABE QUE ESTA ES UNA LISTA DE 'ProductPlaceholder'
    val products = listOf(
        ProductPlaceholder(1, "Silla Gamer", "250.990"),
        ProductPlaceholder(2, "Mesa Gamer", "110.990"),
        ProductPlaceholder(3, "Microfono", "80.000"),
        ProductPlaceholder(4, "Audifonos Gamer", "20.990"),
        ProductPlaceholder(5, "Cooler Pad", "15.000"),
        ProductPlaceholder(6, "Mouse Gamer", "12.000")
    )

    Scaffold(
        topBar = { LevelUpHeader(title = "Level UP", viewModel = viewModel) },
        bottomBar = { LevelUpBottomNavigation(selectedTab = "home", onTabSelected = onNavigate) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(products) { product ->
                    // 2. SOLUCIÓN AL ERROR 'Unresolved reference ProductItem'
                    ProductItem(product)
                }
            }
        }
    }
}

// 3. DEFINICIÓN DEL COMPONENTE VISUAL PARA CADA PRODUCTO
@Composable
fun ProductItem(product: ProductPlaceholder) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(130.dp).padding(4.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant, // GRIS ADAPTATIVO
                border = BorderStroke(1.dp, Color.LightGray)
            ) { }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = product.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$ ${product.price}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5877FF) // MANTENEMOS TU AZUL DE MARCA
            )
        }
    }
}