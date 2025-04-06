package com.example.proyectopoli.screens.viewmodel
// app/src/main/java/com/example/proyectopoli/screens/fragments/content/videos/VideosViewModel.kt



// VideosViewModel.kt
// VideosViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectopoli.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideosViewModel : ViewModel() {
    // 1. Define el StateFlow privado
    private val _uiState = MutableStateFlow<VideosUiState>(VideosUiState.Loading)

    // 2. Expone el StateFlow público
    val uiState: StateFlow<VideosUiState> = _uiState.asStateFlow()

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = VideosUiState.Loading
            try {
                val products = ProductDataSource.getProducts()
                _uiState.value = VideosUiState.Success(products)
            } catch (e: Exception) {
                _uiState.value = VideosUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    fun loadProductById(productId: String) {
        viewModelScope.launch {
            _uiState.value = VideosUiState.Loading
            try {
                val product = ProductDataSource.getProductById(productId)
                if (product != null) {
                    _uiState.value = VideosUiState.Success(listOf(product))
                } else {
                    _uiState.value = VideosUiState.Error("Producto no encontrado")
                }
            } catch (e: Exception) {
                _uiState.value = VideosUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

sealed class VideosUiState {
    object Loading : VideosUiState()
    data class Error(val message: String) : VideosUiState()
    data class Success(val data: List<Product>) : VideosUiState()  // Asegúrate que Product está definido
}