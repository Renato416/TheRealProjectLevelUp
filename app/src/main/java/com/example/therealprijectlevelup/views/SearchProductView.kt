package com.example.therealprijectlevelup.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.therealprijectlevelup.models.domain.ProductDomain
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

    // ✅ OBSERVAMOS EL STATEFLOW CORRECTO
    val products by homeViewModel.filteredProducts.collectAsState()

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
                placeholder = { Text("Buscar producto...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        modifier = Modifier.clickable {
                            if (query.isNotEmpty()) query = ""
                            else onBack()
                        }
                    )
                },
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background,
                    dividerColor = Color.Transparent
                )
            ) {

                if (query.isNotBlank()) {

                    val filtered = products.filter {
                        it.name.contains(query, ignoreCase = true)
                    }

                    LazyColumn {
                        items(filtered) { product: ProductDomain ->
                            Text(
                                text = product.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onNavigate("detail/${product.id}")
                                    }
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
