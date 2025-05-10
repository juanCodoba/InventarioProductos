package com.example.proyectopoli.mock

import Purchase
import UserPreferences
import UserProfile

    object ProfileMockDataSource {
        fun getMockProfile(): UserProfile {
            return UserProfile(
                id = "user-123",
                name = "Juanito Alimaña",
                email = "juanito@example.com",
                phone = "+57 123 456 7890",
                avatarUrl = "android.resource://com.example.proyectopoli/drawable/perfil",
                preferences = UserPreferences(
                    watchType = "Smart Watch",
                    brand = "Samsung"
                ),
                purchaseHistory = listOf(
                    Purchase(
                        id = "purchase-1",
                        detProductUrl = "android.resource://com.example.proyectopoli/drawable/watch_apple_series3",
                        productName = "Apple Watch",
                        description = "Series 3, Nike",
                        date = "01/03/2025",

                    ),
                    Purchase(
                        id = "purchase-2",
                        detProductUrl = "android.resource://com.example.proyectopoli/drawable/watch_samsung_galaxy4",
                        productName = "Samsung Galaxy",
                        description = "Watch 4 Classic",
                        date = "15/12/2024"
                    )
                )
            )
        }
    }

