package br.com.khodahafez.pokersale.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.pokersale.ui.CreateScreen
import br.com.khodahafez.pokersale.ui.views.bounty_type.BountyTypeScreen
import br.com.khodahafez.pokersale.ui.views.login.LoginScreen
import br.com.khodahafez.pokersale.ui.views.match.register.RegisterMatchDataEntryScreen
import br.com.khodahafez.pokersale.ui.views.match.register.RegisterMatchDataUserScreen
import br.com.khodahafez.pokersale.ui.views.match.register.RegisterMatchScreen
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

        composable(route = ScreenEnum.BOUNTY_TYPE.route) {
            BountyTypeScreen()
        }

        composable(route = ScreenEnum.REGISTER_MATCH_DATA_FOR_ENTRY.route) {
            RegisterMatchScreen(
                idMatchCreated = "-Nni6R4NaoiA3ciZ849w",
//                onClickInCardPlayer = {
//                    navController.navigate(
//                        ScreenEnum.REGISTER_MATCH_DATA_USER.route.plus()
//                    )
//                }
            )
//            RegisterMatchDataEntryScreen(
//                onContinueFlow = { idCreatedMatch ->
//                    navController.navigate(
//                        ScreenEnum.REGISTER_MATCH.route.plus(idCreatedMatch)
//                    )
//                }
//            )
        }

        composable(route = ScreenEnum.REGISTER_MATCH.route.plus(ScreensConstants.REGISTER_MATCH_ARGUMENT)) { backStackEntry ->
            RegisterMatchScreen(
                idMatchCreated = backStackEntry.arguments?.getString(
                    "idMatchCreated"
                ).orEmpty(),
//                onClickInCardPlayer = {
//                    navController.navigate(
//                        ScreenEnum.REGISTER_MATCH_DATA_USER.route.plus()
//                    )
//                }
            )
        }

        composable(
            route = ScreenEnum.REGISTER_MATCH_DATA_USER.route.plus(
                ScreensConstants.REGISTER_MATCH_DATA_USER_ARGUMENT
            )
        ) { backStackEntry ->
//            RegisterMatchDataUserScreen(
//                player = backStackEntry.arguments?.getParcelable(
//                    "player", Player::class.java
//                )as Player
//            )
        }
    }
}

