package com.example.proyectopoli.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectopoli.data.mock.MockData
import com.example.proyectopoli.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class FotosViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<FotosUiState>(FotosUiState.Loading)
    val uiState: StateFlow<FotosUiState> = _uiState

    private val _selectedTab = MutableStateFlow(0) // Para controlar la pestaña seleccionada
    val selectedTab: StateFlow<Int> = _selectedTab

    fun loadProducts(category: String = "Tendencia") {
        viewModelScope.launch {
            _uiState.value = FotosUiState.Loading
            try {
                val products = when (category) {
                    "Tendencia" -> MockData.products.filter { it.rating >= 4.0 }
                    "Recientes" -> MockData.products.sortedByDescending { it.id.toInt() }.take(5)
                    "recomendados" -> MockData.products.filter { it.isFeatured }
                    else -> MockData.products
                }
                _uiState.value = FotosUiState.Success(products)
            } catch (e: Exception) {
                _uiState.value = FotosUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun selectTab(index: Int) {
        _selectedTab.value = index
        loadProducts(
            when (index) {
                0 -> "Tendencia"
                1 -> "Recientes"
                2 -> "recomendados"
                else -> "Tendencia"
            }
        )
    }

    init {
        loadProducts()
    }
}

sealed interface FotosUiState {
    object Loading : FotosUiState
    data class Success(val products: List<Product>) : FotosUiState
    data class Error(val message: String) : FotosUiState
}