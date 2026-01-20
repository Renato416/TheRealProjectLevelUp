package com.example.therealprijectlevelup.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.* // Importa SearchBarDefaults y colores
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.therealprijectlevelup.viewModels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchProductView(
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    homeViewModel: HomeViewModel
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    // Obtenemos la lista de productos directamente del ViewModel
    val products = homeViewModel.products.value

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                query = query,
                onQueryChange = { query = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text(text = "Buscar producto...") },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier.clickable {
                            if (query.isNotEmpty()) query = "" else onBack()
                        }
                    )
                },
                // --- AQUÍ ESTÁ LA CORRECCIÓN ---
                // Forzamos el color del contenedor a ser igual al fondo de la pantalla
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background,
                    dividerColor = Color.Transparent // Elimina la línea divisoria si aparece
                )
                // ------------------------------
            ) {
                if (query.isNotEmpty()) {
                    // Lógica de filtrado idéntica a la del profesor
                    val filterProducts = products.filter {
                        it.name.contains(query, ignoreCase = true)
                    }

                    LazyColumn {
                        items(filterProducts) { product ->
                            Text(
                                text = product.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        // Navegar al detalle
                                        onNavigate("detail/${product.id}")
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}