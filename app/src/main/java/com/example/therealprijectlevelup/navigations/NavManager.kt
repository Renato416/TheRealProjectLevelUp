package com.example.therealprijectlevelup.navigations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.*
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

    NavHost(navController, startDestination = "login") {

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
                onNavigate = { navController.navigate(it) },
                homeViewModel = homeViewModel,
                settingsViewModel = settingsViewModel
            )
        }

        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L

            ProductDetailView(
                productId = id,
                homeViewModel = homeViewModel,
                settingsViewModel = settingsViewModel,
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() },
                onNavigate = { navController.navigate(it) }
            )
        }

        composable("cart") {
            CartView(
                onNavigate = { navController.navigate(it) },
                settingsViewModel = settingsViewModel,
                cartViewModel = cartViewModel
            )
        }

        composable("messages") {
            ChatView(
                onNavigate = { navController.navigate(it) },
                settingsViewModel = settingsViewModel,
                chatViewModel = chatViewModel
            )
        }

        composable("profile") {
            ProfileView(
                onNavigate = { navController.navigate(it) },
                viewModel = settingsViewModel,
                profileViewModel = profileViewModel
            )
        }
    }
}
