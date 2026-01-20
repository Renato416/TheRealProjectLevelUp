package com.example.therealprijectlevelup.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therealprijectlevelup.models.Product
import com.example.therealprijectlevelup.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    // Estado de carga para que la UI sepa cuando mostrar el spinner
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 1. OBTENEMOS LA LISTA DEL DOMINIO
                val domainList = repository.getProducts()

                // 2. LA CONVERTIMOS (MAPEO) AL MODELO DE UI (Product)
                val uiList = domainList.map { domain ->
                    Product(
                        id = domain.id,
                        name = domain.name,
                        price = domain.price,
                        // Nota: domain usa 'imageUrl', Product usa 'imageName'. Las conectamos aquí.
                        imageName = domain.imageUrl,
                        description = domain.description,
                        rating = domain.rating
                    )
                }

                // 3. GUARDAMOS LA LISTA YA CONVERTIDA
                _products.value = uiList

            } catch (e: Exception) {
                // Aquí podrías manejar errores (ej. lista vacía o mensaje de error)
                _products.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Función auxiliar para buscar por ID (útil para el detalle)
    fun getProductById(id: Int): Product? {
        return _products.value.find { it.id == id.toLong() }
    }
}