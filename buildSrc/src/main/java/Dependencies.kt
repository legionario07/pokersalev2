object Dependencies {

    val depComposeActivity = lib("androidx.activity:activity-compose", Versions.COMPOSE_ACTIVITY)
    val depComposeNavigation = lib("androidx.navigation:navigation-compose", Versions.COMPOSE_NAVIGATION)
    val depComposeIcons = lib("androidx.compose.material:material-icons-extended")
    val depComposeBom = lib("androidx.compose:compose-bom", Versions.COMPOSE_BOM)
    val depComposeUi = lib("androidx.compose.ui:ui")
    val depComposeUiGraphics = lib("androidx.compose.ui:ui-graphics")
    val depComposeUiTooling = lib("androidx.compose.ui:ui-tooling")
    val depComposeUiToolingPreview = lib("androidx.compose.ui:ui-tooling-preview")
    val depComposeUiTestJUnit = lib("androidx.compose.ui:ui-test-junit4")
    val depComposeUiTestManifest = lib("androidx.compose.ui:ui-test-manifest")
    val depComposeMaterial3 = lib("androidx.compose.material3:material3")
    val depComposeFoundation = lib("androidx.compose.foundation:foundation")
    val depComposeMaterial = lib("androidx.compose.material:material")
    val depJunit = lib("junit:junit", Versions.JUNIT)
    val depJunitExt = lib("androidx.test.ext:junit", Versions.JUNIT_EXT)
    val depMaterial = lib("com.google.android.material:material", Versions.MATERIAL)
    val depExpressoCore = lib("androidx.test.espresso:espresso-core", Versions.EXPRESSO_CORE)
    val depAppCompat = lib("androidx.appcompat:appcompat", Versions.APP_COMPAT)
    val depCoreKtx = lib("androidx.core:core-ktx", Versions.CORE_KTX)
    val depLifecycleRuntimeKtx = lib("androidx.lifecycle:lifecycle-runtime-ktx", Versions.LIFECYCLE_RUNTIME_KTX)
    val depTruth = lib("com.google.truth:truth", Versions.TRUTH)
    val depMockk = lib("io.mockk:mockk", Versions.MOCKK)

    val depFirebaseBom = lib("com.google.firebase:firebase-bom", Versions.FIREBASE_BOM)
    val depFirebaseDatabaseKtx = lib("com.google.firebase:firebase-database-ktx")

    val depHiltAndroid = lib("com.google.dagger:hilt-android", Versions.HILT_ANDROID)
    val depHiltAndroidKapt = lib("com.google.dagger:hilt-compiler", Versions.HILT_ANDROID)

    val depRoomRuntime = lib("androidx.room:room-runtime", Versions.ROOM_DATABASE)
    val depRoomCompile = lib("androidx.room:room-compiler", Versions.ROOM_DATABASE)
    val depRoomKtx = lib("androidx.room:room-ktx", Versions.ROOM_DATABASE)
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
