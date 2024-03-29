import Dependencies

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "br.com.khodahafez.pokersale"
    compileSdk = 33
    buildToolsVersion = "33.0.0"

    defaultConfig {
        applicationId = "br.com.khodahafez.pokersale"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "33.0.0"
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(Dependencies.depCoreKtx)
    implementation(Dependencies.depAppCompat)
    implementation(Dependencies.depMaterial)
    implementation(Dependencies.depLifecycleRuntimeKtx)
    implementation(Dependencies.depComposeActivity)
    implementation(Dependencies.depComposeNavigation)
    implementation(Dependencies.depComposeIcons)
    implementation(platform(Dependencies.depComposeBom))
    implementation(Dependencies.depComposeUi)
    implementation(Dependencies.depComposeUiGraphics)
    implementation(Dependencies.depComposeUiToolingPreview)
    implementation(Dependencies.depComposeMaterial3)
    implementation("com.google.firebase:firebase-database-ktx:20.1.0")
    testImplementation(Dependencies.depJunit)
    androidTestImplementation(Dependencies.depJunitExt)
    androidTestImplementation(Dependencies.depExpressoCore)
    androidTestImplementation(platform(Dependencies.depComposeBom))
    androidTestImplementation(Dependencies.depComposeUiTestJUnit)
    debugImplementation(Dependencies.depComposeUiTooling)
    debugImplementation(Dependencies.depComposeUiTestManifest)

    // Import the BoM for the Firebase platform
    implementation(platform(Dependencies.depFirebaseBom))

    // Add the dependency for the Realtime Database library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(Dependencies.depFirebaseDatabaseKtx)

}
