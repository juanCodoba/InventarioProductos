package com.example.proyectopoli.ui.theme.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuantitySelector(
    currentQuantity: Int,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { if (currentQuantity > 1) onQuantityChange(currentQuantity - 1) },
            modifier = Modifier.size(24.dp)
        ) {
            Text("-", fontSize = 18.sp)
        }

        Text(
            text = currentQuantity.toString(),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        IconButton(
            onClick = { onQuantityChange(currentQuantity + 1) },
            modifier = Modifier.size(24.dp)
        ) {
            Text("+", fontSize = 18.sp)
        }
    }
}