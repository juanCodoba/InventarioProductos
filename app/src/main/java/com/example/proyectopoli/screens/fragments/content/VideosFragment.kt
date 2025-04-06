import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyectopoli.components.ProductCard
import com.example.proyectopoli.screens.fragments.content.menu.AppScreens
import com.example.proyectopoli.screens.viewmodel.VideosUiState
import com.example.proyectopoli.screens.viewmodel.VideosViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import com.example.proyectopoli.components.ProductListView
import com.example.proyectopoli.components.VideoPlayer
import com.example.proyectopoli.model.Product

@Composable
fun VideosFragment(
    navController: NavController,
    productId: String? = null,
    viewModel: VideosViewModel = viewModel()
) {
    // Cargar el producto si hay un ID
    LaunchedEffect(productId) {
        productId?.let { viewModel.loadProductById(it) }
    }

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        when {
            productId != null -> {
                when (uiState) {
                    is VideosUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        )
                    }
                    is VideosUiState.Error -> {
                        val error = (uiState as VideosUiState.Error).message
                        Text(
                            text = "Error: $error",
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Volver")
                        }
                    }
                    is VideosUiState.Success -> {
                        val product = (uiState as VideosUiState.Success).data.firstOrNull()
                        if (product != null) {
                            ProductDetailView(product = product, navController = navController)
                        } else {
                            Text("Producto no encontrado", modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
            else -> {
            }
        }
    }
}
@Composable
private fun ProductDetailView(
    product: Product,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botón de retroceso
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(Icons.Default.ArrowBack, "Volver")
        }

        // Imagen del producto
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        // Reproductor de video si está disponible
        if (!product.videoUrl.isNullOrEmpty()) {
            VideoPlayer(
                videoUrl = product.videoUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f/9f)
                    .padding(vertical = 8.dp)
            )
        }

        // Detalles del producto
        Text(
            text = product.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = "$${product.price}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 4.dp)
        )

        Text(
            text = product.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Botón para comprar/añadir al carrito
        Button(
            onClick = { /* Lógica para añadir al carrito */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text("Añadir al carrito")
        }

        // Opiniones de expertos
        Text(
            text = "Opiniones de expertos:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp)
        )

        product.expertReviews.forEach { review ->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = review.expertName,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = review.expertRole,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = review.comment,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}