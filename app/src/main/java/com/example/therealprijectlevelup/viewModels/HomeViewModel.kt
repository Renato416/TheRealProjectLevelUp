package com.example.therealprijectlevelup.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therealprijectlevelup.models.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val products = mutableStateOf<List<Product>>(emptyList())

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            delay(1000) // Simula carga de BD / API

            products.value = listOf(
                Product(1, "Silla Gamer", "250.990"),
                Product(2, "Mesa Gamer", "110.990"),
                Product(3, "Microfono", "80.000"),
                Product(4, "Audifonos Gamer", "20.990"),
                Product(5, "Cooler Pad", "15.000"),
                Product(6, "Mouse Gamer", "12.000")
            )
        }
    }
}
