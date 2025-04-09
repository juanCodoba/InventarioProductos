package com.example.proyectopoli.ui.theme.components

import UserPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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