package com.example.proyectopoli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.proyectopoli.screens.HomeScreen
import com.example.proyectopoli.ui.theme.ProyectoPOLITheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoPOLITheme {
                // La forma correcta de inicializar la navegación
                val navController = rememberNavController()
                HomeScreen()
            }
        }
    }
}