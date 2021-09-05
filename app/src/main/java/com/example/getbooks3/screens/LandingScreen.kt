package com.example.getbooks3.screens

import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.getbooks3.ui.components.BookCard
import com.example.getbooks3.ui.components.SearchBar
import com.example.getbooks3.ui.components.StdSpace


@Composable
fun LandingScreen(navController: NavController){
   Render()
}

@Preview(widthDp = 300)
@Composable
private fun Render(){
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .padding(all = StdSpace.l.value)
        ) {
            Text(
                text = "Get Books!",
                style = MaterialTheme.typography.h4,
            )
            Spacer(modifier = Modifier.height(StdSpace.m.value))
            SearchBar(placeholder = "Find your favourite book", onClick = {
                Log.d("test", "test")
            })
            Spacer(modifier = Modifier.height(StdSpace.l.value))
        }


        repeat(5) {
            Text(
                text = "Horror",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(all = StdSpace.l.value)
            )

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.width(StdSpace.l.value))
                repeat(10) {
                    BookCard("The Shining", "Stephen King", "test")
                    Spacer(modifier = Modifier.width(StdSpace.l.value))
                }
            }

            Spacer(modifier = Modifier.height(StdSpace.l.value))
        }
    }
}