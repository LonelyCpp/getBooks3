package com.example.getbooks3.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(placeholder: String="", onClick: () -> Unit) {

    Card(
        elevation = 0f.dp,
        modifier = Modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(30f.dp),
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.primary.copy(alpha = 0.5f))
                .padding(horizontal = 12f.dp, vertical = 8f.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "search for books",
                modifier = Modifier.size(14f.dp)
            )
            Spacer(modifier = Modifier.width(StdSpace.s.value))
            Text(
                text = placeholder,
                style = MaterialTheme.typography.caption.merge(
                    TextStyle(
                        color = MaterialTheme.colors.secondary.copy(alpha = 0.6f)
                    )
                ),
                modifier = Modifier.weight(weight = 1f ,fill = true)
            )
        }

    }

}

@Preview(widthDp = 300)
@Composable
private fun Story(){
    SearchBar("Search!", onClick = {  })
}