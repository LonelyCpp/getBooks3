package com.example.getbooks3.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@Composable
fun BookCard(
    title: String,
    author: String,
    imageId: String,
){
    Column() {
        Image(
            painter = rememberImagePainter("https://images.gr-assets.com/authors/1362814142p5/3389.jpg"),
            contentDescription = null,
            modifier = Modifier.size(width = 120f.dp, height = 180f.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = title, style = MaterialTheme.typography.body1.plus(TextStyle(fontWeight = FontWeight.Bold)))
        Text(text = author, style = MaterialTheme.typography.caption)
    }
}

@Preview(widthDp = 300)
@Composable
private fun Story(){
    BookCard("The Shining", "Stephen King", "test")
}