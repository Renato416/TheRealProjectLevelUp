package com.example.therealprijectlevelup.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
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
    val products = homeViewModel.products.value

    // 1. ESTADO DEL SCROLL
    val listState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    // 2. CONTROL DE FOCO PARA EL BUSCADOR
    val searchFocusRequester = remember { FocusRequester() }

    // 3. LÓGICA: MOSTRAR BOTÓN SI EL HEADER YA NO SE VE (INDEX > 0)
    val showFloatingButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,

        // ¡IMPORTANTE! ELIMINAMOS EL topBar DE AQUÍ PARA QUE EL HEADER PUEDA SCROLLEAR
        // topBar = { LevelUpHeader(...) }, <-- ESTO YA NO VA AQUÍ

        floatingActionButton = {
            AnimatedVisibility(
                visible = showFloatingButton,
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            // A. VOLVER ARRIBA SUAVEMENTE
                            listState.animateScrollToItem(0)
                            // B. ACTIVAR EL TECLADO EN EL BUSCADOR
                            searchFocusRequester.requestFocus()
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

        LazyVerticalGrid(
            state = listState, // CONECTAMOS EL ESTADO
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()) // SOLO PADDING ABAJO
            // QUITAMOS EL PADDING TOP PORQUE EL HEADER AHORA ES PARTE DE LA LISTA
            ,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // --- AQUÍ ESTÁ EL TRUCO ---
            // PONEMOS EL HEADER COMO EL PRIMER ITEM DE LA LISTA
            item(span = { GridItemSpan(2) }) {
                LevelUpHeader(
                    title = "Level UP",
                    viewModel = settingsViewModel,
                    searchFocusRequester = searchFocusRequester // PASAMOS EL CONTROL
                )
            }
            // --------------------------

            // LUEGO LOS PRODUCTOS NORMALMENTE (CON MARGEN A LOS LADOS)
            items(products) { product ->
                Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                    ProductItem(product)
                }
            }

            // ESPACIO FINAL PARA QUE EL BOTÓN NO TAPE EL ÚLTIMO PRODUCTO
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

// TU COMPONENTE PRODUCTITEM SE MANTIENE IGUAL QUE ANTES (CON EL FIX DEL COLOR BLANCO)
@Composable
fun ProductItem(product: Product) {
    var visible by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.9f,
        label = "scaleAnimation"
    )

    LaunchedEffect(Unit) {
        visible = true
    }

    Card(
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
                fontSize = 20.sp
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
                onClick = { /* LÓGICA DE COMPRA */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5877FF),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
            ) {
                Text(
                    text = "Comprar",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
        }
    }
}