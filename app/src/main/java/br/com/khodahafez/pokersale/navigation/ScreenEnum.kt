package br.com.khodahafez.pokersale.navigation

internal enum class ScreenEnum(
    val route: String,
    val displayName: String
) {
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
}
