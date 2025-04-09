package com.example.proyectopoli.screens.fragments.content.menu

sealed class AppScreens(val route: String, val title: String) {
    object Perfil : AppScreens("perfil", "Perfil")
    object Fotos : AppScreens("fotos", "Fotos")
    object Videos : AppScreens("videos", "Videos")
    object Web : AppScreens("web", "Web")
    object Botones : AppScreens("botones", "Botones")
    object Cart : AppScreens("cart", "Carrito") // Nueva pantalla
    object VideoDetail : AppScreens("videos/{productId}", "Video Detail") {
        fun createRoute(productId: String) = "videos/$productId"
    }

    companion object {
        fun fromRoute(route: String?): AppScreens {
            return when {
                route?.startsWith(VideoDetail.route) == true -> VideoDetail
                route == Perfil.route -> Perfil
                route == Fotos.route -> Fotos
                route == Videos.route -> Videos
                route == Web.route -> Web
                route == Botones.route -> Botones
                else -> Perfil
            }
        }
    }
}