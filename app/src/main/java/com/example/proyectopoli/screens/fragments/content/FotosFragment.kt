package com.example.proyectopoli.screens.fragments.content

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectopoli.ui.theme.components.ProductCard
import com.example.proyectopoli.data.mock.MockData
import com.example.proyectopoli.model.Product
import com.example.proyectopoli.screens.viewmodel.FotosViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.text.font.FontWeight
import com.example.proyectopoli.features.cart.CartViewModel
import com.example.proyectopoli.screens.viewmodel.FotosUiState
import com.example.proyectopoli.ui.theme.components.ProductGrid

@Composable
fun FotosFragment(
    navController: NavController,
    viewModel: FotosViewModel = viewModel(),
            cartViewModel: CartViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val tabs = listOf("Tendencia", "Recientes", "recomendados")

    Column(modifier = Modifier.fillMaxSize()) {
        // Título
        Text(
            text = "Encuentra el reloj perfecto",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // Pestañas
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.fillMaxWidth(),
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { viewModel.selectTab(index) },
                    text = { Text(text = title) }
                )
            }
        }

        // Contenido según estado
        when (uiState) {
            is FotosUiState.Loading -> LoadingScreen(modifier = Modifier.weight(1f))
            is FotosUiState.Success -> {
                val products = (uiState as FotosUiState.Success).products
                if (products.isEmpty()) {
                    EmptyProductsScreen(modifier = Modifier.weight(1f))
                } else {
                    ProductGrid(
                        products = products,
                        navController = navController,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            is FotosUiState.Error -> {
                ErrorScreen(
                    message = (uiState as FotosUiState.Error).message,
                    onRetry = { viewModel.loadProducts() },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ProductGrid(
    products: List<Product>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                onClick = { navController.navigate("videos/${product.id}") }
            )
        }
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyProductsScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text("No hay productos en esta categoría")
    }
}

@Composable
private fun ErrorScreen(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error: $message",
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}