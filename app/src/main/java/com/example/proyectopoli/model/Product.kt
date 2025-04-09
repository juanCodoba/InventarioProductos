package com.example.proyectopoli.model



import androidx.annotation.DrawableRes

/**
 * Modelo principal de producto con toda la información necesaria.
 * @param id Identificador único (usamos String para compatibilidad con Firebase/APIs)
 * @param videoUrl URL opcional para video demostrativo
 * @param expertReviews Lista de reseñas de expertos (puede estar vacía)
 */
data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val rating: Double,
    @DrawableRes val imageUrl: Int,
    val videoUrl: String? = null,
    val expertReviews: List<ExpertReview> = emptyList(),
    val features: List<String> = emptyList(),
    val isFeatured: Boolean = false,
    val stock: Int = 0
) {
    /**
     * Precio formateado según estándar colombiano
     */
    val formattedPrice: String
        get() = "$${"%,.0f".format(price).replace(",", ".")}"

    /**
     * Rating redondeado a 1 decimal
     */
    val formattedRating: String
        get() = "%.1f".format(rating)
}

data class ExpertReview(
    val expertName: String,
    val expertRole: String,
    val comment: String,
    val rating: Int // 0-10
)