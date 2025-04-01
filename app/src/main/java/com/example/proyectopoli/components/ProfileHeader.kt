package com.example.proyectopoli.components

import UserProfile
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.compose.ui.res.painterResource
import com.example.proyectopoli.R

@Composable
fun ProfileHeader(
    profile: UserProfile,
    modifier: Modifier = Modifier,
    avatarSize: Int = 120,  // Parámetro configurable
    showName: Boolean = true  // Para controlar visibilidad del nombre
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = profile.avatarUrl,
            contentDescription = "Foto de perfil de ${profile.name}",
            modifier = Modifier
                .size(avatarSize.dp)
                .clip(CircleShape),
            placeholder = painterResource(R.drawable.ic_profile_placeholder),
            error = painterResource(R.drawable.ic_broken_image),
            contentScale = ContentScale.Crop
        )

        if (showName) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = profile.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}