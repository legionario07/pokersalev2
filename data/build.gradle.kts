
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "br.com.khodahafez.data"
    compileSdk = 33

    defaultConfig {

        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildToolsVersion = "30.0.3"
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
}