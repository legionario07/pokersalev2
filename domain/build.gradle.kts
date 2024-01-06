plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "br.com.khodahafez.domain"
    compileSdk = 33

    defaultConfig {

        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildToolsVersion = "33.0.0"
}

dependencies {

    implementation(Dependencies.depCoreKtx)
    implementation(Dependencies.depAppCompat)
    implementation(Dependencies.depMaterial)
    implementation("com.google.firebase:firebase-database-ktx:20.1.0")
    testImplementation(Dependencies.depJunit)
    androidTestImplementation(Dependencies.depJunitExt)
    androidTestImplementation(Dependencies.depExpressoCore)
}