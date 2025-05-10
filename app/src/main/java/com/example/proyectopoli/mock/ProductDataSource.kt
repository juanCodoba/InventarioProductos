// data/mock/ProductMockRepository.kt
package com.example.proyectopoli.data.mock

import com.example.proyectopoli.repository.ProductRepository
import com.example.proyectopoli.R
import com.example.proyectopoli.model.ExpertReview
import com.example.proyectopoli.model.Product
import kotlinx.coroutines.delay

/**
 * Implementación mock del repositorio de productos para pruebas y desarrollo
 */
class ProductMockRepository : ProductRepository {

    // Simula un retraso de red
    private suspend fun simulateNetworkDelay() {
        delay(300) // Reducido a 300ms para mejor experiencia de desarrollo
    }

    override suspend fun getAllProducts(): List<Product> {
        simulateNetworkDelay()
        return MockData.products
    }

    override suspend fun getProductById(id: String): Product? {
        simulateNetworkDelay()
        return MockData.products.find { it.id == id }
    }

    override suspend fun getFeaturedProducts(): List<Product> {
        simulateNetworkDelay()
        return MockData.products.filter { it.isFeatured }
    }
}

// Objeto con los datos mockeados
internal object MockData {
    // Reviews de expertos reutilizables
    private val expertReview1 = ExpertReview(
        expertName = "Anotás Quizzán",
        expertRole = "Experto en wearables",
        comment = "Buena relación calidad-precio, pero con sensores algo desactualizados.",
        rating = 7
    )

    private val expertReview2 = ExpertReview(
        expertName = "San Jiménez",
        expertRole = "Analista de gadgets",
        comment = "Dinámico y con buena autonomía, ideal para deportistas ocasionales.",
        rating = 8
    )

    private val expertReview3 = ExpertReview(
        expertName = "TecnoReview",
        expertRole = "Especialista en wearables",
        comment = "Excelente relación calidad-precio",
        rating = 9
    )

    val products = listOf(
        Product(
            id = "1",
            name = "MOTO WATCH 70",
            price = 199990.0, // Precio en formato colombiano (sin decimales)
            description = "Reloj inteligente con seguimiento de actividad avanzada y pantalla AMOLED.",
            expertReviews = listOf(expertReview1, expertReview2),
            rating = 3.5,
            imageUrl = R.drawable.watch0_1,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
            features = listOf("Resistente al agua", "Monitor de ritmo cardíaco"),
            isFeatured = true,
            stock = 15
        ),
        Product(
            id = "2",
            name = "Apple Watch SE 2023",
            price = 249990.0,
            description = "Reloj con GPS integrado y monitorización de frecuencia cardiaca.",
            expertReviews = listOf(expertReview3),
            rating = 4.2,
            imageUrl = R.drawable.watch4_1,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            features = listOf("GPS integrado", "Monitor de sueño", "Resistente al agua 50m"),
            isFeatured = true,
            stock = 8
        ),
        Product(
            id = "4",
            name = "SAMSUNG GALAXY WATCH 4",
            price = 800000.0,
            description = "Reloj inteligente con Wear OS y monitorización avanzada de salud.",
            expertReviews = listOf(expertReview2),
            rating = 4.0,
            imageUrl = R.drawable.watch_samsung_galaxy4,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            features = listOf("Wear OS", "Análisis de composición corporal"),
            isFeatured = false,
            stock = 12
        )
    )
}