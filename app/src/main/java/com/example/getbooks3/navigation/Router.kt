package com.example.getbooks3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.getbooks3.screens.BookScreen
import com.example.getbooks3.screens.LandingScreen

@Composable
fun createRouter(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = RouteNames.LANDING.value) {
        composable(RouteNames.LANDING.value) { LandingScreen(navController) }
        composable(RouteNames.BOOK.value) { BookScreen(navController) }
    }
}