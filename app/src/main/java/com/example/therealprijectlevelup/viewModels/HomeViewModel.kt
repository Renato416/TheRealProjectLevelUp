package com.example.therealprijectlevelup.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therealprijectlevelup.models.domain.ProductDomain
import com.example.therealprijectlevelup.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<ProductDomain>>(emptyList())
    val products: StateFlow<List<ProductDomain>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val searchText = MutableStateFlow("")

    init {
        loadProducts()
    }

    val filteredProducts: StateFlow<List<ProductDomain>> =
        combine(products, searchText) { products, query ->
            if (query.isBlank()) products
            else products.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    private fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _products.value = productRepository.getProducts()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProductById(id: Long): ProductDomain? {
        return _products.value.firstOrNull { it.id == id }
    }
}
