package com.example.proyectopoli.ui.theme.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CartBottomBar(
    total: Double,
    onClearCart: () -> Unit,
    onCheckout: () -> Unit,
    onContinueShopping: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Botón Eliminar todo
        OutlinedButton(
            onClick = onClearCart,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Eliminar todo")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botones principales
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Seguir comprando
            OutlinedButton(
                onClick = onContinueShopping,
                modifier = Modifier.weight(1f)
            ) {
                Text("Seguir comprando")
            }

            // Comprar ahora
            Button(
                onClick = onCheckout,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Text("Comprar ahora")
            }
        }
    }
}