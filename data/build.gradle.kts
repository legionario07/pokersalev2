
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "br.com.khodahafez.data"
    compileSdk = 34

    defaultConfig {

        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles = "consumer-rules.pro"
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

    implementation(project(":domain"))
    implementation(Dependencies.depCoreKtx)
    implementation(Dependencies.depAppCompat)
    implementation(Dependencies.depMaterial)
    implementation("com.google.firebase:firebase-database-ktx:20.1.0")
    testImplementation(Dependencies.depJunit)
    testImplementation(Dependencies.depMockk)
    testImplementation(Dependencies.depTruth)
    androidTestImplementation(Dependencies.depJunitExt)
    androidTestImplementation(Dependencies.depExpressoCore)
    implementation(Dependencies.depLifecycleRuntimeKtx)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation(Dependencies.depRoomRuntime)
    implementation(Dependencies.depRoomKtx)
}