package com.example.proyectopoli.screens.fragments.content

import ProfileViewModel
import UserProfile
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// Coil (Imágenes desde URL)
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.proyectopoli.components.PersonalInfoCard
import com.example.proyectopoli.components.PreferencesCard
import com.example.proyectopoli.components.ProfileHeader
import com.example.proyectopoli.components.PurchaseHistorySection

// ViewModel y Modelos


// Componentes locales (ajusta la ruta según tu estructura)
/*import com.example.proyectopoli.presentation.profile.components.ProfileHeader
import com.example.proyectopoli.presentation.profile.components.PreferencesCard
import com.example.proyectopoli.presentation.profile.components.PersonalInfoCard
import com.example.proyectopoli.presentation.profile.components.PurchaseHistorySection
*/

@Composable
fun PerfilFragment(
    viewModel: ProfileViewModel = viewModel()

) {
    val profile by viewModel.profileState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            profile != null -> {
                ProfileContent(
                    profile = profile!!,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                )
            }
            else -> {
                Text(
                    "Error al cargar el perfil",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun ProfileContent(profile: UserProfile, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header con avatar
        ProfileHeader(profile)

        Spacer(modifier = Modifier.height(24.dp))

        // Sección de preferencias
        PreferencesCard(preferences = profile.preferences)

        Spacer(modifier = Modifier.height(16.dp))

        // Información personal
        PersonalInfoCard(
            fullName = profile.name,
            email = profile.email,
            phone = profile.phone
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Historial de compras
        PurchaseHistorySection(purchases = profile.purchaseHistory)
    }
}