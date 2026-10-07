package com.example.bukuapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.bukuapp.ui.navigation.AppNavigation
import com.example.bukuapp.ui.theme.BukuAppTheme
import com.example.bukuapp.ui.viewmodel.BookViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BukuAppTheme {
                val navController = rememberNavController()
                // BookViewModel dipakai bersama di level NavHost / Activity
                val bookViewModel: BookViewModel = viewModel()

                AppNavigation(
                    navController = navController,
                    viewModel = bookViewModel
                )
            }
        }
    }
}
