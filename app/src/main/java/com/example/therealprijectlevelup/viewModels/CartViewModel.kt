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

    // Estado para el Total
    private val _totalPrice = MutableStateFlow(0)
    val totalPrice: StateFlow<Int> = _totalPrice.asStateFlow()

    init {
        // Cargar carrito guardado al iniciar
        viewModelScope.launch {
            settingsStore.getCartItems.collect { items ->
                _cartItems.value = items
                calculateTotal(items)
            }
        }
    }

    // 1. AGREGAR PRODUCTO (Desde Detalle)
    fun addToCart(product: Product) {
        viewModelScope.launch {
            val currentList = _cartItems.value.toMutableList()
            val existingItem = currentList.find { it.id == product.id }

            if (existingItem != null) {
                // Si ya existe, aumentamos cantidad
                val index = currentList.indexOf(existingItem)
                currentList[index] = existingItem.copy(quantity = existingItem.quantity + 1)
            } else {
                // Si no existe, creamos uno nuevo
                // Limpiamos el precio (quitamos puntos: "250.990" -> 250990)
                val cleanPrice = product.price.replace(".", "").toIntOrNull() ?: 0

                currentList.add(
                    CartItem(
                        id = product.id,
                        name = product.name,
                        quantity = 1,
                        price = cleanPrice,
                        imageRes = product.imageRes
                    )
                )
            }
            updateCart(currentList)
        }
    }

    // 2. AUMENTAR CANTIDAD (+)
    fun increaseQuantity(item: CartItem) {
        val currentList = _cartItems.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == item.id }
        if (index != -1) {
            currentList[index] = item.copy(quantity = item.quantity + 1)
            updateCart(currentList)
        }
    }

    // 3. DISMINUIR CANTIDAD (-)
    fun decreaseQuantity(item: CartItem) {
        val currentList = _cartItems.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == item.id }
        if (index != -1) {
            if (item.quantity > 1) {
                currentList[index] = item.copy(quantity = item.quantity - 1)
            } else {
                // Si baja de 1, lo eliminamos
                currentList.removeAt(index)
            }
            updateCart(currentList)
        }
    }

    // Helper para guardar y recalcular
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