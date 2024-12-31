package br.com.khodahafez.pokersale.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PokerChipHeader(
    modifier: Modifier = Modifier,
    value: String,
    style: TextStyle = MaterialTheme.typography.labelSmall
) {
    Box(
        modifier = modifier
            .size(70.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xE51F62FF), Color(0xFF81D4DA), Color(0xFFE51F62)),
                    center = Offset(100f, 100f),
                    radius = 400f
                ),
                shape = CircleShape
            )
            .border(
                width = 8.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFE51F62), Color(0xFFB3E5FC), Color(0xE51F62FF)),
                ),
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.White, shape = CircleShape)
                .border(
                    width = 4.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xD7415AFF), Color(0xFFE3F2FD), Color(0xD7415AFF)),
                    ), CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(
                        vertical = 2.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    modifier = Modifier.padding(
                        vertical = 1.dp,
                        horizontal = 2.dp
                    ),
                    text = value,
                    style = style,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE51F62),
                    textAlign = TextAlign.Center
                )

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PokerChipHeaderPreview() {
    val value by remember {
        mutableStateOf("Rebuy Duplo")
    }

    PokerChipHeader(
         value = value
    )
}
