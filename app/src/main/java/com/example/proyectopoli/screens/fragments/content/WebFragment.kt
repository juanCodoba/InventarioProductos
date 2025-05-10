package com.example.proyectopoli.screens.fragments.content

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.proyectopoli.ui.theme.green

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebFragment(navController: NavController) {
    // Estado para guardar la URL introducida por el usuario
    var urlInput by remember { mutableStateOf("https://www.google.com") }
    // Estado para guardar la URL que debe cargar el WebView
    var urlToLoad by remember { mutableStateOf(urlInput) }
    // Estado para mostrar el indicador de carga
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Título de la sección
        Text(
            text = "Visita los sitios oficiales",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onBackground
        )

        // Sección de botones de marcas predefinidas
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Marcas populares",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BrandButton("Apple") {
                        urlInput = "https://www.apple.com/es/apple-watch-se/"
                        urlToLoad = urlInput
                        isLoading = true
                    }
                    BrandButton("Samsung") {
                        urlInput = "https://www.samsung.com/latin/watches/galaxy-watch/galaxy-watch4-black-bluetooth-sm-r870nzkalta/?msockid=20fcd7535009628c3a81c50c51a663ca"
                        urlToLoad = urlInput
                        isLoading = true
                    }
                    BrandButton("Motorola") {
                        urlInput = "https://motowatch.com/es/moto-watch-70/"
                        urlToLoad = urlInput
                        isLoading = true
                    }
                }
            }
        }

        // Sección superior para introducir la URL
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = urlInput,
                    onValueChange = { urlInput = it },
                    label = { Text("URL web oficial") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = green,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedLabelColor = green
                    )
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Button(
                    onClick = { 
                        isLoading = true
                        urlToLoad = urlInput 
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = green),
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("Cargar")
                }
            }
        }

        // Indicador de carga (si está cargando)
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = green
            )
        }

        // Contenedor del WebView con bordes redondeados
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
        ) {
            // WebView para mostrar la página
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                isLoading = false // Ocultar indicador cuando termina la carga
                            }
                        }
                        settings.javaScriptEnabled = true
                    }
                },
                update = { webView ->
                    webView.loadUrl(urlToLoad)
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun BrandButton(brand: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = green.copy(alpha = 0.9f)
        ),
        modifier = Modifier
            .padding(4.dp)
            .height(40.dp)
    ) {
        Text(brand)
    }
}