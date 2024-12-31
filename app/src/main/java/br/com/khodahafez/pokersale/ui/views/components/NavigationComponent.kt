package br.com.khodahafez.pokersale.ui.views.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.khodahafez.pokersale.navigation.ScreenEnum

@Composable
fun currentRoute(navController: NavHostController): ScreenEnum {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return ScreenEnum.getScreenEnumByRoute(navBackStackEntry?.destination?.route)
}
