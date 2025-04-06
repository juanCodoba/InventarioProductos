package com.example.proyectopoli.screens.fragments.content.menu

enum class AppScreens(val route: String,val title: String) {
    Perfil("perfil","Perfil"),
    Fotos("fotos","Fotos"),
    Videos("videos","Videos"),
    Web("web","Web"),
    Botones("botones","Botones"),
    VideoDetail("videos/{productId}", "Videos");

    companion object {
        fun createVideoDetailRoute(productId: String) = "videos/$productId"

        fun fromRoute(route: String?): AppScreens {
            return entries.find {
                route?.startsWith(it.route.removeSuffix("/{productId}")) == true
            } ?: Perfil
        }
    }
}