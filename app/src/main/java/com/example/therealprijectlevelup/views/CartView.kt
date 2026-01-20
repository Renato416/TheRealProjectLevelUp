package com.example.therealprijectlevelup.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage // NECESARIO
import com.example.therealprijectlevelup.models.CartItem
import com.example.therealprijectlevelup.viewModels.CartViewModel
import com.example.therealprijectlevelup.viewModels.SettingsViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CartView(
    onNavigate: (String) -> Unit,
    settingsViewModel: SettingsViewModel,
    cartViewModel: CartViewModel
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            LevelUpHeader(
                title = "Carrito",
                viewModel = settingsViewModel,
                onSearchClick = { onNavigate("search") }
            )
        },
        bottomBar = { LevelUpBottomNavigation("cart", onNavigate) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            if (cartItems.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("Tu carrito está vacío", color = Color.Gray, fontSize = 16.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cartItems) { item ->
                        CartItemRow(
                            item = item,
                            onIncrease = { cartViewModel.increaseQuantity(item) },
                            onDecrease = { cartViewModel.decreaseQuantity(item) },
                            onItemClick = { onNavigate("detail/${item.id}") }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Total a Pagar", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            text = numberFormat.format(totalPrice),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Button(
                        onClick = { /* PAGO */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5877FF), contentColor = Color.White),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Pagar", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onItemClick: () -> Unit
) {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onItemClick() },
        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // USAMOS ASYNCIMAGE AQUÍ
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier.size(80.dp).padding(4.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    maxLines = 2
                )
                Text(
                    text = numberFormat.format(item.price),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF5877FF),
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp)).padding(4.dp)
            ) {
                IconButton(onClick = onDecrease, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Default.Remove, contentDescription = "-", tint = Color.Black, modifier = Modifier.size(16.dp))
                }
                Text(
                    text = "${item.quantity}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                IconButton(onClick = onIncrease, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Default.Add, contentDescription = "+", tint = Color.Black, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}