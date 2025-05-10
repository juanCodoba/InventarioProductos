package com.example.proyectopoli.screens

import CartScreen
import VideosFragment
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectopoli.R
import com.example.proyectopoli.features.cart.CartViewModel
import com.example.proyectopoli.screens.fragments.content.*
import com.example.proyectopoli.screens.fragments.content.menu.AppScreens
import com.example.proyectopoli.screens.fragments.content.menu.MenuFragment
import com.example.proyectopoli.screens.viewmodel.FotosViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val currentScreen = AppScreens.fromRoute(currentRoute)

    // Añadir ViewModel del carrito
    val cartViewModel: CartViewModel = viewModel()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                MenuFragment(
                    currentScreen = currentScreen,
                    onOptionSelected = { screen ->
                        scope.launch {
                            drawerState.close()
                            navController.navigate(screen.route) {
                                launchSingleTop = true
                                popUpTo(navController.graph.findStartDestination()?.id ?: return@navigate) {
                                    saveState = true
                                }
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentScreen?.title ?: "App") },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    },
                    actions = {
                        if (currentScreen == AppScreens.Fotos) {
                            IconButton(onClick = { /* Buscar */ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.search),
                                    contentDescription = "Buscar",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            // Icono del carrito con badge
                            val cartItemCount by cartViewModel.cartItems.collectAsState()
                            IconButton(onClick = { navController.navigate("cart") }) {
                                BadgedBox(
                                    badge = {
                                        if (cartItemCount.isNotEmpty()) {
                                            Badge {
                                                Text(cartItemCount.size.toString())
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.cart),
                                        contentDescription = "Carrito",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                        actionIconContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = AppScreens.Fotos.route
                ) {
                    composable(AppScreens.Perfil.route) { PerfilFragment(navController) }
                    composable(AppScreens.Fotos.route) {
                        val fotosViewModel: FotosViewModel = viewModel()
                        FotosFragment(navController, fotosViewModel, cartViewModel)
                    }
                    composable(AppScreens.Videos.route) { VideosFragment(navController) }
                    composable(AppScreens.Web.route) { WebFragment(navController) }
                    composable(AppScreens.Botones.route) { BotonesFragment(navController) }

                    composable(
                        route = AppScreens.VideoDetail.route,
                        arguments = listOf(
                            navArgument("productId") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getString("productId") ?: ""
                        VideosFragment(navController, productId)
                    }

                    // Añadir ruta para el carrito
                    composable("cart") {
                        CartScreen(navController, cartViewModel)
                    }
                }
            }
        }
    }
}