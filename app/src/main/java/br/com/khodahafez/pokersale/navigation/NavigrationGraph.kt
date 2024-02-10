package br.com.khodahafez.pokersale.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.khodahafez.pokersale.ui.views.balance.BalanceAllScreen
import br.com.khodahafez.pokersale.ui.views.balance.NewBalanceScreen
import br.com.khodahafez.pokersale.ui.views.balance.my_balance.MyBalanceScreen
import br.com.khodahafez.pokersale.ui.views.balance.ranking_balance.RankingBalanceScreen
import br.com.khodahafez.pokersale.ui.views.bounty_type.BountyTypeScreen
import br.com.khodahafez.pokersale.ui.views.home.HomeScreen
import br.com.khodahafez.pokersale.ui.views.login.LoginScreen
import br.com.khodahafez.pokersale.ui.views.match.register.RegisterMatchDataEntryScreen
import br.com.khodahafez.pokersale.ui.views.match.register.RegisterMatchScreen
import br.com.khodahafez.pokersale.ui.views.player.PlayerScreen
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
            HomeScreen()
        }

        composable(route = ScreenEnum.POSITION_SCORE.route) {
            PositionScoreScreen()
        }

        composable(route = ScreenEnum.BOUNTY_TYPE.route) {
            BountyTypeScreen()
        }

        composable(route = ScreenEnum.REGISTER_MATCH_DATA_FOR_ENTRY.route) {
            RegisterMatchDataEntryScreen(
                onContinueFlow = { idCreatedMatch ->
                    navController.navigate(
                        ScreenEnum.REGISTER_MATCH.route.plus(idCreatedMatch)
                    )
                }
            )
        }

        composable(route = ScreenEnum.REGISTER_MATCH.route.plus(ScreensConstants.REGISTER_MATCH_ARGUMENT)) { backStackEntry ->
            RegisterMatchScreen(
                idMatchCreated = backStackEntry.arguments?.getString(
                    "idMatchCreated"
                ).orEmpty(),
                onSaveMatchSuccessful = {
                    navController.navigate(ScreenEnum.NEW_BALANCE_POKER_SALE.route) {
                        launchSingleTop = true
                        popUpTo(ScreenEnum.HOME.route)
                    }
                }
            )
        }

        composable(
            route = ScreenEnum.NEW_BALANCE_POKER_SALE.route
        ) {
            NewBalanceScreen(
                onSuccessFinish = { navController.popBackStack() }
            )
        }

        composable(
            route = ScreenEnum.REGISTER_PLAYER.route
        ) {
            PlayerScreen(
                onSaveSuccessful = { navController.navigate(ScreenEnum.HOME.route) }
            )
        }

        composable(
            route = ScreenEnum.MY_BALANCE.route
        ) {
            MyBalanceScreen()
        }

        composable(
            route = ScreenEnum.RANKING_BALANCE.route
        ) {
            RankingBalanceScreen()
        }

        composable(
            route = ScreenEnum.BALANCE_POKER_SALE.route
        ) {
            BalanceAllScreen(

            )
        }
    }
}


