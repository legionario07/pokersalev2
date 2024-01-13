package br.com.khodahafez.pokersale.ui.views.tabbar

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.khodahafez.pokersale.ui.views.components.currentRoute
import com.google.android.material.bottomnavigation.BottomNavigationItemView

@Composable
internal fun PokerSaleBottomNavigation(
    navController: NavHostController,
    items: Set<TabItem>
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,
        elevation = 4.dp,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        val currentRoute = currentRoute(navController)
        items.iterator().forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = "") },
                selectedContentColor = MaterialTheme.colorScheme.secondary,
                unselectedContentColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f) ,
                selected = currentRoute.route == screen.route.route,
                alwaysShowLabel = false,
                onClick = {
                    if (currentRoute.route != screen.route.route) {
                        navController.navigate(screen.route.route)
                    }
                }
            )
        }
    }
}
