package com.example.bukuapp.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bukuapp.ui.screen.DetailScreen
import com.example.bukuapp.ui.screen.HomeScreen
import com.example.bukuapp.ui.viewmodel.BookViewModel

// Pengaturan NavHost dengan rute home dan detail/{bookKey}
@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: BookViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        // Rute 1: Layar utama (Home)
        composable(route = "home") {
            HomeScreen(
                viewModel = viewModel,
                onBookClick = { rawKey ->
                    // Encode kunci buku karena mengandung slash (misal /works/OL123W)
                    val encodedKey = Uri.encode(rawKey)
                    navController.navigate("detail/$encodedKey")
                }
            )
        }

        // Rute 2: Layar detail buku (Detail)
        composable(
            route = "detail/{bookKey}",
            arguments = listOf(
                navArgument("bookKey") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val encodedKey = backStackEntry.arguments?.getString("bookKey") ?: ""
            // Decode kembali kunci buku
            val decodedKey = Uri.decode(encodedKey)

            DetailScreen(
                bookKey = decodedKey,
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
