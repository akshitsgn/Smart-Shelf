package com.example.bookapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost() {
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = "bookScreen") {
        composable("bookScreen") {
            BookScreen(
                onNavigateToAddBook = { navController.navigate("addBookScreen") }
            )
        }
        composable("addBookScreen") {
            AddBookScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
