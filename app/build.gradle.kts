import Dependencies

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "br.com.khodahafez.pokersale"
    compileSdk = AndroidConfig.COMPILE_SDK

    defaultConfig {
        applicationId ="br.com.khodahafez.pokersale"
        minSdk = AndroidConfig.MIN_SDK
        targetSdk = AndroidConfig.TARGET_SDK
        versionCode = AndroidConfig.VERSION_CODE_APP
        versionName = AndroidConfig.VERSION_NAME_APP

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dependencies.depCoreKtx)
    implementation(Dependencies.depAppCompat)
    implementation(Dependencies.depMaterial)
    implementation(Dependencies.depLifecycleRuntimeKtx)
    implementation(Dependencies.depComposeActivity)
    implementation(platform(Dependencies.depComposeBom))
    implementation(Dependencies.depComposeUi)
    implementation(Dependencies.depComposeUiGraphics)
    implementation(Dependencies.depComposeUiToolingPreview)
    implementation(Dependencies.depComposeMaterial3)
    testImplementation(Dependencies.depJunit)
    androidTestImplementation(Dependencies.depJunitExt)
    androidTestImplementation(Dependencies.depExpressoCore)
    androidTestImplementation(platform(Dependencies.depComposeBom))
    androidTestImplementation(Dependencies.depComposeUiTestJUnit)
    debugImplementation(Dependencies.depComposeUiTooling)
    debugImplementation(Dependencies.depComposeUiTestManifest)
}