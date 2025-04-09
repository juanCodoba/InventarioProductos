// screens/viewmodel/VideosViewModel.kt
package com.example.proyectopoli.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectopoli.model.Product
import com.example.proyectopoli.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideosViewModel(
    private val repository: ProductRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState: StateFlow<ProductDetailUiState> = _uiState

    sealed interface ProductDetailUiState {
        object Loading : ProductDetailUiState
        data class Success(val product: Product) : ProductDetailUiState
        data class Error(val message: String) : ProductDetailUiState
    }

    fun loadProductDetail(productId: String) {
        viewModelScope.launch {
            _uiState.value = ProductDetailUiState.Loading
            try {
                val product = repository.getProductById(productId)
                _uiState.value = if (product != null) {
                    ProductDetailUiState.Success(product)
                } else {
                    ProductDetailUiState.Error("Producto no encontrado")
                }
            } catch (e: Exception) {
                _uiState.value = ProductDetailUiState.Error(
                    e.message ?: "Error al cargar el producto"
                )
            }
        }
    }
}