package com.example.proyectopoli.features.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectopoli.model.CartItem
import com.example.proyectopoli.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // Total calculado reactivamente
    val total: StateFlow<Double> = _cartItems.map { items ->
        items.sumOf { it.subTotal }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    // Contador de items para el badge del carrito
    val itemCount: StateFlow<Int> = _cartItems.map { it.size }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    /**
     * Añade un producto al carrito o incrementa su cantidad si ya existe
     */
    fun addToCart(product: Product) {
        viewModelScope.launch {
            _cartItems.update { currentItems ->
                val existingItem = currentItems.find { it.id == product.id }

                if (existingItem != null) {
                    currentItems.map {
                        if (it.id == product.id) it.copy(quantity = it.quantity + 1)
                        else it
                    }
                } else {
                    currentItems + CartItem(
                        id = product.id,
                        name = product.name,
                        price = product.price,
                        quantity = 1,
                        imageUrl = product.imageUrl.toString()
                    )
                }
            }
        }
    }

    /**
     * Elimina un item del carrito por su ID
     */
    fun removeItem(itemId: String) {
        viewModelScope.launch {
            _cartItems.update { currentItems ->
                currentItems.filter { it.id != itemId }
            }
        }
    }

    /**
     * Actualiza la cantidad de un item específico
     * @param itemId ID del producto
     * @param newQuantity Nueva cantidad (mínimo 1)
     */
    fun updateQuantity(itemId: String, newQuantity: Int) {
        viewModelScope.launch {
            _cartItems.update { currentItems ->
                currentItems.map {
                    if (it.id == itemId) it.copy(quantity = newQuantity.coerceAtLeast(1))
                    else it
                }
            }
        }
    }

    /**
     * Vacía completamente el carrito
     */
    fun clearCart() {
        viewModelScope.launch {
            _cartItems.value = emptyList()
        }
    }

    /**
     * Verifica si un producto ya está en el carrito
     */
    fun isProductInCart(productId: String): Boolean {
        return _cartItems.value.any { it.id == productId }
    }
}