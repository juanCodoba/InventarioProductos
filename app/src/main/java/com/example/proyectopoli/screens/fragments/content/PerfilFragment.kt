package com.example.proyectopoli.screens.fragments.content

import ProfileViewModel
import UserProfile
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectopoli.ui.theme.components.PersonalInfoCard
import com.example.proyectopoli.ui.theme.components.ProfileHeaderWithPreferences

// Colores personalizados
val DarkGray = Color(0xFFE0E0E0)    // Fondo gris oscuro
val DarkGreen = Color(0xFF2E7D32)   // Verde oscuro
val LightText = Color(0xFFFFFFFF)   // Texto claro

@Composable
fun PerfilFragment(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {
    val profile by viewModel.profileState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = LightText
            )
            profile != null -> ProfileContent(profile = profile!!)
            else -> Text(
                "Error al cargar el perfil",
                color = LightText,
                modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun ProfileContent(profile: UserProfile) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Sección superior (Avatar + Preferencias)
        ProfileHeaderWithPreferences(
            profile = profile,
            preferences = profile.preferences,
            modifier = Modifier.padding(bottom = 8.dp),
            containerColor = DarkGreen,
            contentColor = LightText,
            preferenceContainerColor = Color.White.copy(alpha = 0.2f)
        )

        CustomDivider()

        // Información personal
        PersonalInfoCard(
            fullName = profile.name,
            email = profile.email,
            phone = profile.phone,
            modifier = Modifier.padding(vertical = 8.dp),

        )

        CustomDivider()

        // Historial de compras
        PurchaseHistorySection(
            purchases = profile.purchaseHistory,
            modifier = Modifier.padding(top = 8.dp),

        )
    }
}

@Composable
private fun CustomDivider() {
    Divider(
        modifier = Modifier.padding(vertical = 16.dp),
        color = Color.LightGray.copy(alpha = 0.3f),
        thickness = 1.dp
    )
}