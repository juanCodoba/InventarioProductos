package com.example.proyectopoli.repository

// data/repository/ProductRepository.kt
import com.example.proyectopoli.model.Product

interface ProductRepository {
    suspend fun getAllProducts(): List<Product>
    suspend fun getProductById(id: String): Product?
    suspend fun getFeaturedProducts(): List<Product>
}