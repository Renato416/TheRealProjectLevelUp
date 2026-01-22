package com.example.therealprijectlevelup.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therealprijectlevelup.models.Product
import com.example.therealprijectlevelup.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    /* -------------------- STATE -------------------- */

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val searchText = MutableStateFlow("")

    /* -------------------- DERIVED STATE -------------------- */

    val filteredProducts: StateFlow<List<Product>> =
        combine(_products, searchText) { products, query ->
            if (query.isBlank()) {
                products
            } else {
                products.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    /* -------------------- INIT -------------------- */

    init {
        loadProducts()
    }

    /* -------------------- ACTIONS -------------------- */

    private fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = productRepository.getProducts()

                _products.value = result.map {
                    Product(
                        id = it.id,
                        name = it.name,
                        price = it.price,
                        imageName = it.imageUrl,
                        description = it.description,
                        rating = it.rating
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _products.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateSearchText(text: String) {
        searchText.value = text
    }

    fun getProductById(id: Int): Product? {
        return _products.value.firstOrNull {
            it.id?.toLong() == id.toLong()
        }
    }

}
