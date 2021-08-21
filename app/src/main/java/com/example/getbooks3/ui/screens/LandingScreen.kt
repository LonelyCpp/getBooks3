package com.example.getbooks3.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.getbooks3.navigation.RouteNames

@Composable
fun LandingScreen(navController: NavController){
    Column {
        Text(text = "This is the landing screen")
        Button(onClick = { navController.navigate(RouteNames.BOOK.value) }) {
            Text(text = "go to book screen")
        }
    }

}