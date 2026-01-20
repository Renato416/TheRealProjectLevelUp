package com.example.therealprijectlevelup.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therealprijectlevelup.R
import com.example.therealprijectlevelup.models.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val products = mutableStateOf<List<Product>>(emptyList())

    // 1. ESTADO DE CARGA
    var isLoading = mutableStateOf(false)

    // TEXTO DEL BUSCADOR (Lo mantenemos para la búsqueda)
    var searchText = mutableStateOf("")

    // BANDERA PARA FOCO
    var focusSearchOnEntry = mutableStateOf(false)

    init {
        loadProducts()
    }

    // FILTRO DE BÚSQUEDA
    val filteredProducts: List<Product>
        get() {
            val query = searchText.value
            return if (query.isEmpty()) {
                products.value
            } else {
                products.value.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            }
        }

    private fun loadProducts() {
        viewModelScope.launch {
            // 2. INDICAMOS QUE ESTÁ CARGANDO
            isLoading.value = true

            delay(1000) // Simulamos espera de red

            products.value = listOf(
                Product(
                    1, "Silla Gamer", "250.990", R.drawable.img_silla,
                    "Ergonomía superior para largas sesiones de juego. Soporte lumbar ajustable y material transpirable."
                ),
                Product(
                    2, "Mesa Gamer", "110.990", R.drawable.img_escritorio,
                    "Superficie amplia con textura de fibra de carbono. Incluye soporte para auriculares y vasos."
                ),
                Product(
                    3, "Microfono", "80.000", R.drawable.img_microfono,
                    "Captura de audio nítida con cancelación de ruido. Ideal para streaming y podcasting."
                ),
                Product(
                    4, "Audifonos Gamer", "20.990", R.drawable.img_audifonos,
                    "Sonido envolvente 7.1 y almohadillas de espuma viscoelástica para máxima comodidad."
                ),
                Product(
                    5, "Cooler Pad", "15.000", R.drawable.img_cooler,
                    "Base refrigerante con 4 ventiladores silenciosos e iluminación LED azul."
                ),
                Product(
                    6, "Mouse Gamer", "12.000", R.drawable.img_mause,
                    "Sensor óptico de alta precisión hasta 16000 DPI. Botones programables."
                )
            )

            // 3. TERMINÓ DE CARGAR
            isLoading.value = false
        }
    }

    fun getProductById(id: Int): Product? {
        return products.value.find { it.id == id }
    }
}