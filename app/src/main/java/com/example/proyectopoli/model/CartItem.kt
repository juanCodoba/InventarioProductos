package com.example.proyectopoli.model


data class CartItem(
    val id: String,
    val name: String,
    val price: Double,
    var quantity: Int,
    val imageUrl: String? = null
) {
    val subTotal: Double get() = price * quantity
}