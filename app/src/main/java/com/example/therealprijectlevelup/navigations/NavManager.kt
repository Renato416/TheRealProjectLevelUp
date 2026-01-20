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

    // Forzamos inicio en Login por seguridad
    val startNode = "login"

    NavHost(navController = navController, startDestination = startNode) {

        composable("login") {
            LaunchedEffect(Unit) { loginViewModel.loginResult.value = "" }

            LoginScreen(
                onRegisterClick = {
                    registerViewModel.clean()
                    navController.navigate("register")
                },
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

        composable("search") {
            SearchProductView(
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() },
                homeViewModel = homeViewModel
            )
        }

        // --- RUTA ACTUALIZADA PARA EL CARRITO ---
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            ProductDetailView(
                productId = id,
                homeViewModel = homeViewModel,
                settingsViewModel = settingsViewModel,
                cartViewModel = cartViewModel, // PASAMOS EL VIEWMODEL
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