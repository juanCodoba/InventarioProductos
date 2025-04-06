package com.example.proyectopoli.model


data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val rating: Double,
    val imageUrl: Int,
    val videoUrl: String?, // Nuevo campo para el video
    val expertReviews: List<ExpertReview>,
    // ... otros campos
)


data class ExpertReview(
    val expertName: String,
    val expertRole: String,
    val comment: String,
    val rating: Int // 0-10
)