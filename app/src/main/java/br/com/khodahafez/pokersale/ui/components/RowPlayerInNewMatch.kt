package br.com.khodahafez.pokersale.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun RowPlayerInNewMatch(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFB3E5FC), Color(0xFF81D4DA), Color(0xFF0288D1)),
                    center = Offset(100f, 100f),
                    radius = 400f
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFFFFFF), Color(0xFFB3E5FC), Color(0xFF0288D1)),
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Theodoro",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00796B),
        )
        Spacer(modifier = Modifier.width(4.dp))
        PokerCheckChip(
           onChecked = {isChecked ->
               println(isChecked)
           }
        )
        Spacer(modifier = Modifier.width(4.dp))
        PokerChip(
            value = 2,
            onDecrement = {},
            onIncrement = {}
        )
        Spacer(modifier = Modifier.width(2.dp))
        PokerChip(
            value = 2,
            onDecrement = {},
            onIncrement = {}
        )
        Spacer(modifier = Modifier.width(2.dp))
        PokerChip(
            value = 2,
            onDecrement = {},
            onIncrement = {}
        )
        Spacer(modifier = Modifier.width(2.dp))
        PokerChip(
            value = 2,
            onDecrement = {},
            onIncrement = {}
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RowPlayerInNewMatchPreview() {


    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(count = 10) {
            RowPlayerInNewMatch()
        }
    }
}