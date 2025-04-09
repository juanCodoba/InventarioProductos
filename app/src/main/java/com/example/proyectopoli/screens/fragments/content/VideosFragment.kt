import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyectopoli.data.mock.ProductMockRepository
import com.example.proyectopoli.model.ExpertReview
import com.example.proyectopoli.model.Product
import com.example.proyectopoli.screens.viewmodel.VideosViewModel
import com.example.proyectopoli.ui.theme.components.ProductCard
import com.example.proyectopoli.ui.theme.components.VideoPlayer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.proyectopoli.ui.theme.components.ProductGrid




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideosFragment(
    navController: NavController,
    productId: String? = null, // ID pasado desde FotosFragment
    viewModel: VideosViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return VideosViewModel(ProductMockRepository()) as T
            }
        }
    )
) {
    // Cargar detalles del producto cuando se recibe el ID
    LaunchedEffect(productId) {
        productId?.let { viewModel.loadProductDetail(it) }
    }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (uiState) {
                is VideosViewModel.ProductDetailUiState.Loading -> FullScreenLoader()
                is VideosViewModel.ProductDetailUiState.Error -> {
                    ErrorScreen(
                        message = (uiState as VideosViewModel.ProductDetailUiState.Error).message,
                        onRetry = { productId?.let { viewModel.loadProductDetail(it) } }
                    )
                }
                is VideosViewModel.ProductDetailUiState.Success -> {
                    ProductDetailWithVideo(
                        product = (uiState as VideosViewModel.ProductDetailUiState.Success).product,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductDetailWithVideo(
    product: Product,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sección de video (si existe)
        product.videoUrl?.let { videoUrl ->
            VideoPlayerSection(
                videoUrl = videoUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            )
        }

        // Sección de información del producto
        ProductDetailInfo(
            product = product,
            modifier = Modifier.padding(16.dp)
        )
    }
}



@androidx.annotation.OptIn(UnstableApi::class)
@Composable
private fun VideoPlayerSection(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    val exoPlayer = rememberExoPlayer(videoUrl)

    DisposableEffect(
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                    setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
                }
            },
            modifier = modifier
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}

@Composable
private fun ProductDetailInfo(
    product: Product,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = product.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "$${"%,.0f".format(product.price)}",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF03624C)
        )

        Text(
            text = product.description,
            style = MaterialTheme.typography.bodyLarge
        )

        // Botones de acción
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Botón "Añadir a carrito" (nuevo)
            Button(
                onClick = { /* Acción para añadir al carrito */ },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text("Añadir a carrito")
            }

            // Botón "Comprar ahora" (existente)
            Button(
                onClick = { /* Acción de compra/reserva */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Comprar ahora")
            }
        }

        // Opiniones de expertos
        if (product.expertReviews.isNotEmpty()) {
            Text(
                text = "Opiniones expertas:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                product.expertReviews.forEach { review ->
                    ExpertReviewCard(review = review)
                }
            }
        }
    }
}

@Composable
private fun ExpertReviewCard(review: ExpertReview) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = review.expertName,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = review.expertRole,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = review.comment,
                modifier = Modifier.padding(top = 4.dp)
            )
            RatingBar(
                rating = review.rating.toFloat() / 2, // Convertir de 0-10 a 0-5
                maxStars = 5
            )
        }
    }
}

@Composable
private fun RatingBar(
    rating: Float,
    maxStars: Int = 5,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        for (i in 1..maxStars) {
            Icon(
                imageVector = if (i <= rating) Icons.Filled.Star
                else if (i - 0.5f <= rating) Icons.Filled.StarHalf
                else Icons.Filled.StarOutline,
                contentDescription = "Rating",
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
@Composable
private fun ExpertReviewItem(review: ExpertReview) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = review.expertName, fontWeight = FontWeight.Bold)
        Text(text = review.expertRole, fontStyle = FontStyle.Italic)
        Text(text = review.comment, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
private fun FullScreenLoader() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorMessage(message: String, onRetry: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Error: $message", color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(16.dp))
        onRetry?.let {
            Button(onClick = it) {
                Text("Reintentar")
            }
        }
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun rememberExoPlayer(videoUrl: String): ExoPlayer {
    val context = LocalContext.current
    return remember {
        ExoPlayer.Builder(context)
            .setSeekForwardIncrementMs(10000) // 10 segundos adelante
            .setSeekBackIncrementMs(10000)    // 10 segundos atrás
            .build()
            .apply {
                setMediaItem(MediaItem.fromUri(videoUrl))
                prepare()
                playWhenReady = true
            }
    }.also { player ->
        DisposableEffect(Unit) {
            onDispose {
                player.release()
            }
        }
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    val exoPlayer = rememberExoPlayer(videoUrl)

    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
                useController = true
                setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
            }
        },
        modifier = modifier
    )
}

@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        )
        {
            Text("Reintentar")
        }

    }
}
