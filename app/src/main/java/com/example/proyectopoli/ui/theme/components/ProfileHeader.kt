package com.example.proyectopoli.ui.theme.components

import UserPreferences
import UserProfile
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ProfileHeaderWithPreferences(
    profile: UserProfile,
    preferences: UserPreferences,
    modifier: Modifier = Modifier,
    avatarSize: Int = 100,
    containerColor: Color = Color(0xFF2E7D32),
    contentColor: Color = Color.White,
    preferenceContainerColor: Color = Color.White.copy(alpha = 0.2f)
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally // Centrado horizontal
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally // Contenido centrado
            ) {
                // Avatar (ya centrado)
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(avatarSize.dp)
                        .clip(CircleShape)
                        .border(3.dp, contentColor, CircleShape)
                ) {
                    AsyncImage(
                        model = profile.avatarUrl,
                        contentDescription = "Avatar de ${profile.name}",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Nombre
                Text(
                    text = profile.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Preferencias (con ancho fijo para mejor control)
                Box(
                    modifier = Modifier
                        .width(280.dp) // Ancho fijo para consistencia
                        .background(
                            color = preferenceContainerColor,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center // Centrado interno
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "PREFERENCIAS",
                            style = MaterialTheme.typography.labelSmall,
                            color = contentColor.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                       /* Text(
                            text = "Tipo de reloj:",
                            style = MaterialTheme.typography.labelSmall,
                            color = contentColor.copy(alpha = 0.8f)
                        )
                        Text(
                            text = preferences.watchType,
                            style = MaterialTheme.typography.labelSmall,
                            color = contentColor.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Marca:",
                            style = MaterialTheme.typography.labelSmall,
                            color = contentColor.copy(alpha = 0.8f)
                        )
                        Text(
                            text = preferences.brand,
                            style = MaterialTheme.typography.labelSmall,
                            color = contentColor.copy(alpha = 0.8f)
                        )*/
                        InfoRowCentered(label = "Tipo de reloj:", value = preferences.watchType)
                        Spacer(modifier = Modifier.height(4.dp))
                        InfoRowCentered(label = "Marca:", value = preferences.brand)
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRowCentered(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.9f),  // Blanco con 90% opacidad
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.White  // Blanco sólido
        )
    }

}