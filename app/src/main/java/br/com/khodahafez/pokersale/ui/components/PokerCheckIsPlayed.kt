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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.khodahafez.pokersale.ui.UiConstants.Colors.disableColors
import br.com.khodahafez.pokersale.ui.UiConstants.Colors.enableColors

@Composable
fun PokerCheckIsPlayed(
    modifier: Modifier = Modifier,
    onChecked: (Boolean) -> Unit,
) {

    var isChecked by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .size(30.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = if (isChecked) enableColors else disableColors,
                    center = Offset(100f, 100f),
                    radius = 400f
                ),
                shape = CircleShape
            )
            .border(
                width = 4.dp,
                brush = Brush.linearGradient(
                    colors = if (isChecked) listOf(
                        Color(0xFFFFFFFF),
                        Color(0xFFB3E5FC),
                        Color(0xFF0288D1)
                    )
                    else disableColors,
                ),
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Color.White, shape = CircleShape)
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = if (isChecked)
                            listOf(
                                Color(0xFF81D4F4),
                                Color(0xFFE3F2FD),
                                Color(0xFF81D4FA)
                            )
                        else disableColors,
                    ), CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                IconButton(
                    modifier = Modifier.size(14.dp),
                    onClick = {
                        isChecked = !isChecked
                        onChecked(isChecked)
                    }
                ) {
                    Icon(
                        imageVector = if (isChecked) Icons.Default.CheckCircle else Icons.Default.Close,
                        contentDescription = null,
                        tint = if (isChecked) Color(0xFF0e31f1) else Color.Gray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PokerCheckIsPlayedPreview() {
    PokerCheckIsPlayed(
        onChecked = { isPlayed ->
            println(isPlayed)
        },
    )
}
