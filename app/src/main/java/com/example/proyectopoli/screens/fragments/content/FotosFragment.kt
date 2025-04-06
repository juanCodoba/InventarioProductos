package com.example.proyectopoli.screens.fragments.content

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectopoli.components.ProductCard
import com.example.proyectopoli.screens.fragments.content.menu.AppScreens

@Composable
fun FotosFragment(navController: NavController) {
    val products = remember { ProductDataSource.getProducts() }

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                onClick = {
                    // Usa la función createRoute del enum VideoDetail
                    navController.navigate(AppScreens.createVideoDetailRoute(product.id))                },
                modifier = Modifier.animateContentSize()
            )
        }
    }
}