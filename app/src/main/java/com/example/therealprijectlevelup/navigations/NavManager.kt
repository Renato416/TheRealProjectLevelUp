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
    val userSession by settingsViewModel.userEmail.collectAsState()

    val startNode = if (userSession.isNotEmpty()) "home" else "login"

    NavHost(navController = navController, startDestination = startNode) {

        composable("login") {
            LaunchedEffect(Unit) { loginViewModel.loginResult.value = "" }

            LoginScreen(
                // --- CAMBIO CRÍTICO AQUÍ ---
                // Limpiamos ANTES de navegar. Esto garantiza que la variable "SUCCESS"
                // se borre antes de que la pantalla de registro se cree.
                onRegisterClick = {
                    registerViewModel.clean()
                    navController.navigate("register")
                },
                // ---------------------------
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                viewModel = loginViewModel
            )
        }

        composable("register") {
            // --- ELIMINA EL LaunchedEffect DE AQUÍ ---
            // Ya no lo necesitamos porque limpiamos en el click del login.

            RegisterScreen(
                onBack = { navController.popBackStack() },
                viewModel = registerViewModel
            )
        }

        // ... EL RESTO DE TUS COMPOSABLES (home, search, etc) SIGUEN IGUAL ...
        composable("home") {
            HomeView(
                onNavigate = { route -> navController.navigate(route) },
                homeViewModel = homeViewModel,
                settingsViewModel = settingsViewModel
            )
        }

        composable("search") {
            SearchProductView(
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() },
                homeViewModel = homeViewModel
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
                onBack = { navController.popBackStack() },
                onNavigate = { route -> navController.navigate(route) }
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

        composable("profile") {
            ProfileView(
                onNavigate = { route ->
                    if (route == "login") {
                        loginViewModel.loginResult.value = ""
                        loginViewModel.username.value = ""
                        loginViewModel.password.value = ""
                        navController.navigate("login") {
                            popUpTo(0)
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