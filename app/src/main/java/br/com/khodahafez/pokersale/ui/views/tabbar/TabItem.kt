package br.com.khodahafez.pokersale.ui.views.tabbar

import androidx.compose.ui.graphics.vector.ImageVector
import br.com.khodahafez.domain.PokerSaleConstants.EMPTY_STRING
import br.com.khodahafez.pokersale.navigation.ScreenEnum

internal data class TabItem(
    val title: String = EMPTY_STRING,
    val route: ScreenEnum,
    val icon: ImageVector,
)
