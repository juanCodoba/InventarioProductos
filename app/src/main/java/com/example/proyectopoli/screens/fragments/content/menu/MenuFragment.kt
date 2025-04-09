package com.example.proyectopoli.screens.fragments.content.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MenuFragment(
    currentScreen: AppScreens,
    onOptionSelected: (AppScreens) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = "Menú Principal",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        Divider()

        // Lista de pantallas que deben aparecer en el menú
        val menuScreens = listOf(
            AppScreens.Perfil,
            AppScreens.Fotos,
            AppScreens.Videos,
            AppScreens.Web,
            AppScreens.Botones
        )

        menuScreens.forEach { screen ->
            NavigationDrawerItem(
                label = { Text(screen.title) }, // Usamos title en lugar de route
                selected = currentScreen == screen,
                onClick = { onOptionSelected(screen) },
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}