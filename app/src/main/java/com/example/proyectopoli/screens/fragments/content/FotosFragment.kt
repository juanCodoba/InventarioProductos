package com.example.proyectopoli.screens.fragments.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectopoli.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.em


// Modelo para los relojes
data class PlantillaReloj(
    val id: Int,
    val nombre: String,
    val precio: String,
    val imageResId: Int
)

// Datos de ejemplo basados en el mockup
val sampleWatches = listOf(
    PlantillaReloj(1, "Apple Watch Ultra 2", "$4.400.000", R.drawable.watch_apple_ultra2),
    PlantillaReloj(2, "Motorola Moto Watch 70", "$300.000", R.drawable.watch_motorola_70),
    PlantillaReloj(3, "Samsung Galaxy Watch 4", "$800.000", R.drawable.watch_samsung_galaxy4),
    PlantillaReloj(4, "Apple Watch Series 3, Nike", "$1.800.000", R.drawable.watch_apple_series3),
    PlantillaReloj(5, "Reloj 5", "$1.200.000", R.drawable.watch0_1),
    PlantillaReloj(6, "Reloj 6", "$900.000", R.drawable.watch4_1),
    PlantillaReloj(7, "Reloj 7", "$2.100.000", R.drawable.watch4_3),
    PlantillaReloj(8, "Reloj 9", "$500.000", R.drawable.watch6_1_2)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FotosFragment() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Tendencia", "Recientes", "recomendados")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = "Encuentra el reloj perfecto",
            style = MaterialTheme.typography.headlineMedium.merge(
                TextStyle(lineHeight = 1.1.em) // Ajusta el interlineado para que no se vea pegado el texto al saltar de linea
            ),
            fontWeight = FontWeight.Bold,
            fontSize = 55.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Pestañas
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent, // pone el color de fondo trasnsparente en el seleccionador de pestañas
            contentColor = MaterialTheme.colorScheme.onBackground,
            divider = {} // Oculta el divisor por defecto que aparece en la parte inferior del las opciones
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = selectedTabIndex == index
                Tab(
                    selected = isSelected,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal // pone en negrita la opción si se selecciona
                        )
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary, // Color texto seleccionado
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant // Color texto no seleccionado
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cuadrícula de las imagenes que se muestran en la pestaña seleccionada
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 columnas
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp), // Espacio vertical entre filas
            horizontalArrangement = Arrangement.spacedBy(10.dp) // Espacio horizontal entre columnas
        ) {
            items(sampleWatches) { watch ->
                ProductCard(watch = watch)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(watch: PlantillaReloj) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f / 1.5f),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = { /* Aqui va el detalle (fragmento) del producto al seleccionar la imagen */ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // La columna llena la tarjeta
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally, // Centra el contenido
            verticalArrangement = Arrangement.SpaceBetween // establece un espacio entre la imagen y su descripción
        ) {
            Image(
                painter = painterResource(id = watch.imageResId),
                contentDescription = watch.nombre,
                modifier = Modifier
                    .weight(1f) // La imagen toma el espacio disponible
                    .fillMaxWidth(), // La imagen ocupa el ancho
                contentScale = ContentScale.Fit // Escala la imagen para que quepa
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = watch.nombre,
                style = MaterialTheme.typography.titleLarge.copy( // Se usa Copy en titleLarge para poder modificarlo
                    fontSize = 15.sp,
                    color = Color.Black
                ),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = watch.precio,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF03624C),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}