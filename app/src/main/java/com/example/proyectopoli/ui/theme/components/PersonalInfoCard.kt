package com.example.proyectopoli.ui.theme.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.proyectopoli.R

@Composable
fun PersonalInfoCard(
    fullName: String,
    email: String,
    phone: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.personal_info_title),
                style = MaterialTheme.typography.titleMedium,
                color = contentColor
            )

            Spacer(modifier = Modifier.padding(8.dp))

            InfoRow(
                label = stringResource(R.string.full_name_label),
                value = fullName,
                labelColor = labelColor,
                valueColor = contentColor
            )
            InfoRow(
                label = stringResource(R.string.email_label),
                value = email,
                labelColor = labelColor,
                valueColor = contentColor
            )
            InfoRow(
                label = stringResource(R.string.phone_label),
                value = phone,
                labelColor = labelColor,
                valueColor = contentColor
            )
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    labelColor: Color,
    valueColor: Color
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = labelColor
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = valueColor
        )
    }
}