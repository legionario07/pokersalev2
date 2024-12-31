package br.com.khodahafez.pokersale.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RowPlayerInNewMatchHeader(
    modifier: Modifier = Modifier,
    selected: Int,
    scrollState: ScrollState = rememberScrollState()
) {
    Row(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF3d81c4), Color(0xFF194d80), Color(0xFF3d81c4)),
                    center = Offset(100f, 100f),
                    radius = 400f
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF75b3f0), Color(0xFF3d81c4), Color(0xFF3d81c4)),
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .horizontalScroll(scrollState),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.wrapContentSize()) {
            Text(
                modifier = Modifier.width(90.dp),
                text = "Jogador",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFFfcfbfb),
            )

            Text(
                modifier = Modifier.width(90.dp),
                text = "$selected Selecionados",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFFfcfbfb),
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        PokerChipHeader(
            value = "Rebuy"
        )
        Spacer(modifier = Modifier.width(2.dp))
        PokerChipHeader(
            value = "Reb. Duplo"
        )
        Spacer(modifier = Modifier.width(2.dp))
        PokerChipHeader(
            value = "Addon"
        )
        Spacer(modifier = Modifier.width(2.dp))
        PokerChipHeader(
            value = "Posição"
        )
        Spacer(modifier = Modifier.width(2.dp))
        PokerChipHeader(
            value = "Bountys"
        )
        Spacer(modifier = Modifier.width(2.dp))
        PokerChipHeader(
            value = "Premiação"
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}
