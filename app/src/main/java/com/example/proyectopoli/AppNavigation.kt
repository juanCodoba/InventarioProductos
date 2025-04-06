package com.example.proyectopoli.navigation

import VideosFragment
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectopoli.screens.HomeScreen
import com.example.proyectopoli.screens.fragments.content.*
import com.example.proyectopoli.screens.fragments.content.menu.AppScreens

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home_screen"
    ) {
        // Pantalla principal con drawer
        composable("home_screen") {
            HomeScreen()
        }

        // Pantallas secundarias
        composable(AppScreens.Perfil.route) { PerfilFragment(navController) }
        composable(AppScreens.Fotos.route) { FotosFragment(navController) }
        composable(AppScreens.Videos.route) { VideosFragment(navController) }
        composable(AppScreens.Web.route) { WebFragment(navController) }
        composable(AppScreens.Botones.route) { BotonesFragment(navController) }
    }
}