
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "br.com.khodahafez.domain"
    compileSdk = 34

    defaultConfig {

        minSdk = 27
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
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation(Dependencies.depCoreKtx)
    implementation(Dependencies.depAppCompat)
    implementation(Dependencies.depMaterial)
    implementation("com.google.firebase:firebase-database-ktx:20.1.0")
    testImplementation(Dependencies.depJunit)
    androidTestImplementation(Dependencies.depJunitExt)
    androidTestImplementation(Dependencies.depExpressoCore)

    implementation(Dependencies.depRoomRuntime)
    implementation(Dependencies.depRoomKtx)
    annotationProcessor(Dependencies.depRoomCompile)

    kapt(Dependencies.depRoomCompile)
}