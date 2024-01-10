package br.com.khodahafez.pokersale.ui.views.tabbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.khodahafez.pokersale.navigation.ScreenEnum

internal data class TabItem(
    val title: String,
    val route: ScreenEnum,
    val icon: ImageVector,
)
