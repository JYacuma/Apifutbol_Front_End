package com.example.api.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.api.viewmodel.FutbolViewModel

@Composable
fun FutbolAppNavigation(viewModel: FutbolViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(
                viewModel = viewModel,
                onSplashFinished = {
                    navController.navigate("manager_dashboard") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("manager_dashboard") {
            ManagerDashboardScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = "mercado/{tipoOperacion}",
            arguments = listOf(navArgument("tipoOperacion") { type = NavType.IntType })
        ) { backStackEntry ->
            val tipo = backStackEntry.arguments?.getInt("tipoOperacion") ?: 0
            MercadoScreen(viewModel = viewModel, tipoOperacion = tipo, onBack = { navController.popBackStack() })
        }

        composable(
            route = "detalle_jugador/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            DetalleJugadorScreen(id = id, viewModel = viewModel, onBack = { navController.popBackStack() })
        }

        composable(
            route = "detalle_partido/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            DetallePartidoScreen(viewModel = viewModel, idPartido = id, onBack = { navController.popBackStack() })
        }

        composable("gestionar_tecnico") {
            GestionarTecnicoScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }

        composable("admin_panel") {
            AdminPanelScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
    }
}