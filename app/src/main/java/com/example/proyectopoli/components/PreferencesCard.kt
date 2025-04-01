package com.example.proyectopoli.components

import UserPreferences
import UserProfile
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.proyectopoli.R

@Composable
fun PreferencesCard(preferences: UserPreferences) {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Preferencias", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Tipo de reloj: ${preferences.watchType}")
            Text("Marca: ${preferences.brand}")
        }
    }
}