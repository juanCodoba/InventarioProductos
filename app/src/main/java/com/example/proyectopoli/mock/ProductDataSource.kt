import com.example.proyectopoli.R
import com.example.proyectopoli.model.ExpertReview
import com.example.proyectopoli.model.Product

// Fuente de datos mock (simula una API o base de datos)
object ProductDataSource {
    fun getProducts(): List<Product> = listOf(
        Product(
            id = "1",
            name = "MOTO WATCH 70",
            price = 199.99,
            description = "Reloj inteligente con seguimiento de actividad avanzada y pantalla AMOLED.",
            expertReviews = listOf(
                ExpertReview(
                    expertName = "Anotás Quizzán",
                    expertRole = "Experto en wearables",
                    comment = "Buena relación calidad-precio, pero con sensores algo desactualizados.",
                    rating = 7
                ),
                ExpertReview(
                    expertName = "San Jiménez",
                    expertRole = "Analista de gadgets",
                    comment = "Dinámico y con buena autonomía, ideal para deportistas ocasionales.",
                    rating = 8
                )
            ),
            rating = 3.5,
            imageUrl = R.drawable.watch0_1,
            videoUrl = "https://ejemplo.com/videos/moto-watch-70.mp4"
        ),
        Product(
            id = "2",
            name = "FITTRACK PRO X",
            price = 249.99,
            description = "Reloj con GPS integrado y monitorización de frecuencia cardiaca.",
            expertReviews = listOf(
                ExpertReview(
                    expertName = "Ideal Microfosa",
                    expertRole = "Revista de tecnología",
                    comment = "Precisión en métricas deportivas, pero diseño incómodo.",
                    rating = 6
                )
            ),
            rating = 4.0,
            imageUrl = R.drawable.watch4_1,
            videoUrl = "https://ejemplo.com/videos/moto-watch-70.mp4"


        )
    )

    // Función para obtener un producto por ID (útil para navegación)
    fun getProductById(id: String): Product? {
        return getProducts().firstOrNull { it.id == id }
    }
}