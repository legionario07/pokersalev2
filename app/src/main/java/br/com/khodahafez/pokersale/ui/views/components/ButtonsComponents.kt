package br.com.khodahafez.pokersale.ui.views.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.khodahafez.domain.PokerSaleConstants.EMPTY_STRING

@Composable
fun IconButtonComponent(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    isEnabled: Boolean = true,
    size: Dp = 48.dp,
    contentDescription: String = EMPTY_STRING,
    color: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        enabled = isEnabled,
        onClick = { onClick() }
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = color,
            modifier = Modifier.size(size)
        )
    }
}
