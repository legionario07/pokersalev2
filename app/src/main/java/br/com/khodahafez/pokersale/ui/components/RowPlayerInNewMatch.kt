package br.com.khodahafez.pokersale.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.khodahafez.pokersale.ui.UiConstants.Colors.disableColors
import br.com.khodahafez.pokersale.ui.views.match.register.RegisterMatchScreenModel

private val enabledColorsRow = listOf(Color(0xFFB3E5FC), Color(0xFF81D4DA), Color(0xFF0288D1))
private val enabledColorsBorders = listOf(Color(0xFFFFFFFF), Color(0xFFB3E5FC), Color(0xFF0288D1))

@Composable
fun RowPlayerInNewMatch(
    modifier: Modifier = Modifier,
    player: RegisterMatchScreenModel,
    scrollState: ScrollState = rememberScrollState(),
    isChecked: Boolean,
    onChecked: (Boolean) -> Unit
) {

    var reBuy by rememberSaveable {
        mutableIntStateOf(player.reBuy)
    }

    var doubleReBuy by rememberSaveable {
        mutableIntStateOf(player.doubleReBuy)
    }

    var addOn by rememberSaveable {
        mutableIntStateOf(player.addon)
    }

    var bounties by rememberSaveable {
        mutableIntStateOf(player.bounties)
    }

    var prize by rememberSaveable {
        mutableIntStateOf(player.prize.toInt())
    }

    var position by rememberSaveable {
        mutableIntStateOf(player.position)
    }

    LaunchedEffect(doubleReBuy, reBuy, doubleReBuy, addOn, bounties, prize, position) {
        player.reBuy = reBuy
        player.doubleReBuy = doubleReBuy
        player.addon = addOn
        player.bounties = bounties
        player.prize = prize.toDouble()
        player.position = position
    }

    Row(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = if (isChecked) enabledColorsRow else disableColors,
                    center = Offset(100f, 100f),
                    radius = 400f
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = if (isChecked) enabledColorsBorders else disableColors,
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .horizontalScroll(scrollState),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.width(12.dp))

        PokerCheckIsPlayed(isChecked = isChecked) { isChecked ->
            onChecked(isChecked)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            modifier = Modifier.width(60.dp),
            text = player.player.name,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFF00796B),
        )
        Spacer(modifier = Modifier.width(4.dp))
        PokerChip(
            valueCounter = reBuy,
            onChange = { value ->
                reBuy = value
            }
        )
        Spacer(modifier = Modifier.width(2.dp))
        PokerChip(
            valueCounter = doubleReBuy,
            onChange = { value ->
                doubleReBuy = value
            }
        )
        Spacer(modifier = Modifier.width(2.dp))
        PokerCheckChip(
            addonValue = addOn,
            onChecked = { isChecked ->
                addOn = if (isChecked) 1 else 0
            }
        )
        Spacer(modifier = Modifier.width(2.dp))
        PokerChip(
            valueCounter = position,
            onChange = { value ->
                position = value
            }
        )
        Spacer(modifier = Modifier.width(2.dp))
        PokerChip(
            valueCounter = bounties,
            onChange = { value ->
                bounties = value
            }
        )
        Spacer(modifier = Modifier.width(2.dp))
        PokerChipWithTwoOptionToInc(
            valueCounter = prize,
            incrementValue = 20,
            onChange = { value ->
                prize = value
            }
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}
