package com.example.proyectopoli.navigation

import VideosFragment
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectopoli.screens.HomeScreen
import com.example.proyectopoli.screens.fragments.content.*
import com.example.proyectopoli.screens.fragments.content.menu.AppScreens

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Fotos.route
    ) {
        composable("home_screen") { HomeScreen() }
        composable(AppScreens.Perfil.route) { PerfilFragment(navController) }
        composable(AppScreens.Fotos.route) { FotosFragment(navController) }
        composable(AppScreens.Videos.route) { VideosFragment(navController) }
        composable(AppScreens.Web.route) { WebFragment(navController) }
        composable(AppScreens.Botones.route) { BotonesFragment(navController) }

        composable(
            route = AppScreens.VideoDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            VideosFragment(
                navController = navController,
                productId = productId
            )
        }
    }
}