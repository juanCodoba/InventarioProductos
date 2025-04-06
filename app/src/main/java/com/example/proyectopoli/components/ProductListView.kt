package com.example.proyectopoli.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.proyectopoli.model.Product
import com.example.proyectopoli.screens.viewmodel.VideosUiState

@Composable
fun ProductListView(
    navController: NavController,
    uiState: VideosUiState,
    modifier: Modifier = Modifier,
    onProductClick: (String) -> Unit = { productId ->
        navController.navigate("videos/${productId}")
    }
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is VideosUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is VideosUiState.Error -> {
                // Manejo de error
            }

            is VideosUiState.Success -> {
                ProductGrid(
                    products = uiState.data,
                    onProductClick = onProductClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}