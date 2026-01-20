package com.example.therealprijectlevelup.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therealprijectlevelup.data.SettingsStore
import com.example.therealprijectlevelup.models.CartItem
import com.example.therealprijectlevelup.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val settingsStore: SettingsStore) : ViewModel() {

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

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val currentList = _cartItems.value.toMutableList()
            // Conversión segura de ID y Precio
            val productIdInt = product.id?.toInt() ?: 0
            val priceInt = product.price.toInt()

            val existingItem = currentList.find { it.id == productIdInt }

            if (existingItem != null) {
                val index = currentList.indexOf(existingItem)
                currentList[index] = existingItem.copy(quantity = existingItem.quantity + 1)
            } else {
                currentList.add(
                    CartItem(
                        id = productIdInt,
                        name = product.name,
                        quantity = 1,
                        price = priceInt,
                        imageUrl = product.imageName // AHORA SÍ FUNCIONARÁ
                    )
                )
            }
            updateCart(currentList)
        }
    }

    fun increaseQuantity(item: CartItem) {
        val currentList = _cartItems.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == item.id }
        if (index != -1) {
            currentList[index] = item.copy(quantity = item.quantity + 1)
            updateCart(currentList)
        }
    }

    fun decreaseQuantity(item: CartItem) {
        val currentList = _cartItems.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == item.id }
        if (index != -1) {
            if (item.quantity > 1) {
                currentList[index] = item.copy(quantity = item.quantity - 1)
            } else {
                currentList.removeAt(index)
            }
            updateCart(currentList)
        }
    }

    private fun updateCart(newList: List<CartItem>) {
        _cartItems.value = newList
        calculateTotal(newList)
        viewModelScope.launch {
            settingsStore.saveCart(newList)
        }
    }

    private fun calculateTotal(items: List<CartItem>) {
        var total = 0
        items.forEach { total += (it.price * it.quantity) }
        _totalPrice.value = total
    }
}