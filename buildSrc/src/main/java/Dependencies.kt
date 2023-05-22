object Dependencies {

    val depComposeActivity = lib("androidx.activity:activity-compose", Versions.COMPOSE_ACTIVITY)
    val depComposeBom = lib("androidx.compose:compose-bom", Versions.COMPOSE_BOM)
    val depComposeUi = lib("androidx.compose.ui:ui")
    val depComposeUiGraphics = lib("androidx.compose.ui:ui-graphics")
    val depComposeUiTooling = lib("androidx.compose.ui:ui-tooling")
    val depComposeUiToolingPreview = lib("androidx.compose.ui:ui-tooling-preview")
    val depComposeUiTestJUnit = lib("androidx.compose.ui:ui-test-junit")
    val depComposeUiTestManifest = lib("androidx.compose.ui:ui-test-manifest")
    val depComposeMaterial3 = lib("androidx.compose.material3:material3")
    val depJunit = lib("junit:junit", Versions.JUNIT)
    val depJunitExt = lib("androidx.test.ext:junit", Versions.JUNIT_EXT)
}

fun lib(path: String, version: String = "", suffix: String = ""): String {
    val builder = StringBuilder(path)
    if(version.isNotEmpty()) {
        builder.append(":")
        builder.append(version)
    }

    if(suffix.isNotEmpty()) {
        builder.append(suffix)
    }

    return builder.toString()
}
