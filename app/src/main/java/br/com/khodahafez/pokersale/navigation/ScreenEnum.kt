package br.com.khodahafez.pokersale.navigation

internal enum class ScreenEnum(
    val route: String,
    val displayName: String
) {
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
}
