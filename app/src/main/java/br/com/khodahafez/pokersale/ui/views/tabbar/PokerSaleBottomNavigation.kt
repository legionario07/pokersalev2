package br.com.khodahafez.pokersale.ui.views.tabbar

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.khodahafez.pokersale.R

@Composable
internal fun PokerSaleBottomNavigation(
    navController: NavHostController,
    items: List<TabItem>
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = "") },
                label = { Text(stringResource(id = R.string.poker_sale_login_hint)) },
                selected = currentRoute == screen.route.route,
                alwaysShowLabel = false,
                onClick = {
                    if (currentRoute != screen.route.route) {
                        navController.navigate(screen.route.route)
                    }
                }
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString("KEY_ROUTE")
}