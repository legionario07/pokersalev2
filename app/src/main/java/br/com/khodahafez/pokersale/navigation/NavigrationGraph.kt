package br.com.khodahafez.pokersale.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.khodahafez.pokersale.ui.CreateScreen
import br.com.khodahafez.pokersale.ui.views.login.LoginScreen
import br.com.khodahafez.pokersale.ui.views.position_score.PositionScoreScreen

@Composable
internal fun navigationGraph(
    navController: NavHostController,
    startDestination: ScreenEnum
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        composable(route = ScreenEnum.LOGIN.route) {
            LoginScreen(
                onLoginSuccessful = {
                    navController.navigate(ScreenEnum.HOME.route)
                }
            )
        }

        composable(route = ScreenEnum.HOME.route) {
            CreateScreen(
                mutableListOf()
            )
        }

        composable(route = ScreenEnum.POSITION_SCORE.route) {
            PositionScoreScreen()
        }
    }
}

