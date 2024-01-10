package br.com.khodahafez.pokersale.navigation

import br.com.khodahafez.domain.PokerSaleConstants.EMPTY_STRING
import br.com.khodahafez.pokersale.navigation.ScreensConstants.REGISTER_MATCH_ARGUMENT

internal enum class ScreenEnum(
    val route: String,
    val displayName: String,
    val argument: String = EMPTY_STRING
) {

    REGISTER_PLAYER(
        route = ScreensConstants.REGISTER_PLAYER,
        displayName = ScreensConstants.REGISTER_PLAYER_DISPLAY,
    ),
    REGISTER_MATCH_DATA_USER(
        route = ScreensConstants.REGISTER_MATCH_DATA_USER,
        displayName = ScreensConstants.REGISTER_MATCH_DATA_USER_DISPLAY,
        argument = ScreensConstants.REGISTER_MATCH_DATA_USER_ARGUMENT,
    ),
    REGISTER_MATCH(
        route = ScreensConstants.REGISTER_MATCH,
        displayName = ScreensConstants.REGISTER_MATCH_DISPLAY,
        argument = REGISTER_MATCH_ARGUMENT
    ),
    REGISTER_MATCH_DATA_FOR_ENTRY(
        route = ScreensConstants.REGISTER_MATCH_DATA_ENTRY,
        displayName = ScreensConstants.REGISTER_MATCH_DATA_ENTRY_DISPLAY
    ),
    BALANCE(
        route = ScreensConstants.BALANCE,
        displayName = ScreensConstants.BALANCE_DISPLAY
    ),
    BOUNTY_TYPE(
        route = ScreensConstants.POSITION_SCORE,
        displayName = ScreensConstants.POSITION_SCORE_DISPLAY
    ),
    POSITION_SCORE(
        route = ScreensConstants.POSITION_SCORE,
        displayName = ScreensConstants.POSITION_SCORE_DISPLAY
    ),
    LOGIN(
        route = ScreensConstants.LOGIN,
        displayName = ScreensConstants.LOGIN_DISPLAY
    ),
    HOME(
        route = ScreensConstants.HOME,
        displayName = ScreensConstants.HOME_DISPLAY
    );
}

internal object ScreensConstants {

    const val LOGIN = "login"
    const val LOGIN_DISPLAY = "Login"

    const val HOME = "home"
    const val HOME_DISPLAY = "Home"

    const val POSITION_SCORE = "Position_Score"
    const val POSITION_SCORE_DISPLAY = "Pontos por Posição"

    const val BOUNTY_TYPE_SCORE = "Bounty_Type"
    const val BOUNTY_TYPE_DISPLAY = "Tipos de Bounty"

    const val BALANCE = "Balance"
    const val BALANCE_DISPLAY = "Caixa"

    const val REGISTER_MATCH_DATA_ENTRY = "Match_Register_Data_Entry"
    const val REGISTER_MATCH_DATA_ENTRY_DISPLAY = "Nova Partida"

    const val REGISTER_MATCH = "Match_Register/"
    const val REGISTER_MATCH_DISPLAY = "Nova Partida"
    const val REGISTER_MATCH_ARGUMENT = "{idMatchCreated}"

    const val REGISTER_MATCH_DATA_USER = "REGISTER_MATCH_DATA_USER/"
    const val REGISTER_MATCH_DATA_USER_DISPLAY = "Dados do Usuário"
    const val REGISTER_MATCH_DATA_USER_ARGUMENT = "{player}"

    const val REGISTER_PLAYER = "REGISTER_PLAYER"
    const val REGISTER_PLAYER_DISPLAY = "Novo Jogador"
}
