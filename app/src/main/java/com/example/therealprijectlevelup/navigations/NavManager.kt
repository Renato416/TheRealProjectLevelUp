package com.example.therealprijectlevelup.navigations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.therealprijectlevelup.viewModels.*
import com.example.therealprijectlevelup.views.*

@Composable
fun NavManager(
    settingsViewModel: SettingsViewModel,
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    homeViewModel: HomeViewModel,
    cartViewModel: CartViewModel,
    chatViewModel: ChatViewModel,
    profileViewModel: ProfileViewModel
) {
    val navController = rememberNavController()

    // Verificamos sesión para decidir inicio
    val userSession by settingsViewModel.userEmail.collectAsState()
    val startNode = if (userSession.isNotEmpty()) "home" else "login"

    NavHost(navController = navController, startDestination = startNode) {

        composable("login") {
            // Aseguramos limpieza al entrar (Doble seguridad)
            LaunchedEffect(Unit) {
                loginViewModel.loginResult.value = ""
            }

            LoginScreen(
                onRegisterClick = { navController.navigate("register") },
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                viewModel = loginViewModel
            )
        }

        composable("register") {
            RegisterScreen(
                onBack = { navController.popBackStack() },
                viewModel = registerViewModel
            )
        }

        composable("home") {
            HomeView(
                onNavigate = { route -> navController.navigate(route) },
                homeViewModel = homeViewModel,
                settingsViewModel = settingsViewModel
            )
        }
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            ProductDetailView(
                productId = id,
                homeViewModel = homeViewModel,
                settingsViewModel = settingsViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable("cart") {
            CartView(
                onNavigate = { route -> navController.navigate(route) },
                settingsViewModel = settingsViewModel,
                cartViewModel = cartViewModel
            )
        }

        composable("messages") {
            ChatView(
                onNavigate = { route -> navController.navigate(route) },
                settingsViewModel = settingsViewModel,
                chatViewModel = chatViewModel
            )
        }

        // --- AQUÍ ESTÁ EL ARREGLO DEL REBOTE ---
        composable("profile") {
            ProfileView(
                onNavigate = { route ->
                    if (route == "login") {
                        // 1. MATAMOS AL ZOMBIE: Limpiamos el estado "SUCCESS" manualmente
                        loginViewModel.loginResult.value = ""
                        loginViewModel.username.value = ""
                        loginViewModel.password.value = ""

                        // 2. Ahora sí navegamos seguros al login
                        navController.navigate("login") {
                            popUpTo(0) // Borra todo el historial para que no puedan volver
                        }
                    } else {
                        navController.navigate(route)
                    }
                },
                viewModel = settingsViewModel,
                profileViewModel = profileViewModel
            )
        }
    }
}