package com.example.therealprijectlevelup.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therealprijectlevelup.data.SettingsStore
import com.example.therealprijectlevelup.models.CartItem
import com.example.therealprijectlevelup.models.domain.ProductDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val settingsStore: SettingsStore
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _totalPrice = MutableStateFlow(0)
    val totalPrice: StateFlow<Int> = _totalPrice.asStateFlow()

    init {
        viewModelScope.launch {
            settingsStore.getCartItems.collect { items ->
                _cartItems.value = items
                calculateTotal(items)
            }
        }
    }

    fun addToCart(product: ProductDomain) {
        viewModelScope.launch {
            val list = _cartItems.value.toMutableList()

            val productId = product.id.toInt()
            val price = product.price.toInt()

            val index = list.indexOfFirst { it.id == productId }

            if (index >= 0) {
                val item = list[index]
                list[index] = item.copy(quantity = item.quantity + 1)
            } else {
                list.add(
                    CartItem(
                        id = productId,
                        name = product.name,
                        quantity = 1,
                        price = price,
                        imageUrl = product.imageUrl
                    )
                )
            }

            updateCart(list)
        }
    }

    fun increaseQuantity(item: CartItem) {
        val list = _cartItems.value.toMutableList()
        val index = list.indexOfFirst { it.id == item.id }
        if (index >= 0) {
            list[index] = item.copy(quantity = item.quantity + 1)
            updateCart(list)
        }
    }

    fun decreaseQuantity(item: CartItem) {
        val list = _cartItems.value.toMutableList()
        val index = list.indexOfFirst { it.id == item.id }
        if (index >= 0) {
            if (item.quantity > 1) {
                list[index] = item.copy(quantity = item.quantity - 1)
            } else {
                list.removeAt(index)
            }
            updateCart(list)
        }
    }

    private fun updateCart(list: List<CartItem>) {
        _cartItems.value = list
        calculateTotal(list)
        viewModelScope.launch {
            settingsStore.saveCart(list)
        }
    }

    private fun calculateTotal(items: List<CartItem>) {
        _totalPrice.value = items.sumOf { it.price * it.quantity }
    }
}
