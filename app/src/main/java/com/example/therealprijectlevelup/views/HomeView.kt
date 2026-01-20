package com.example.therealprijectlevelup.views

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.therealprijectlevelup.models.Product
import com.example.therealprijectlevelup.viewModels.HomeViewModel
import com.example.therealprijectlevelup.viewModels.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeView(
    onNavigate: (String) -> Unit,
    homeViewModel: HomeViewModel,
    settingsViewModel: SettingsViewModel
) {
    // 1. OBTENEMOS EL ESTADO DE CARGA
    val isLoading = homeViewModel.isLoading.value
    // 2. USAMOS LA LISTA FILTRADA
    val products = homeViewModel.filteredProducts

    val listState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    // Mostramos el botón si bajamos un poco
    val showFloatingButton by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            AnimatedVisibility(
                visible = showFloatingButton,
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                            onNavigate("search")
                        }
                    },
                    containerColor = Color(0xFF5877FF),
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                }
            }
        },
        bottomBar = {
            LevelUpBottomNavigation("home", onNavigate)
        }
    ) { paddingValues ->

        if (isLoading) {
            // SPINNER DE CARGA
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF5877FF))
            }
        } else {
            // LISTA DE PRODUCTOS
            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item(span = { GridItemSpan(2) }) {
                    LevelUpHeader(
                        title = "Level UP",
                        viewModel = settingsViewModel,
                        onSearchClick = { onNavigate("search") }
                    )
                }

                items(products) { product ->
                    Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                        ProductItem(
                            product = product,
                            onClick = { onNavigate("detail/${product.id}") }
                        )
                    }
                }

                if (products.isEmpty()) {
                    item(span = { GridItemSpan(2) }) {
                        Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                            Text("No se encontraron productos", color = Color.Gray)
                        }
                    }
                }

                item(span = { GridItemSpan(2) }) {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.9f,
        label = "scaleAnimation"
    )

    LaunchedEffect(Unit) {
        visible = true
    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                alpha = scale
            },
        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .size(120.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                fontSize = 20.sp,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$ ${product.price}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5877FF),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
            ) {
                // --- CAMBIO AQUI ---
                Text(
                    text = "Ver más",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
        }
    }
}