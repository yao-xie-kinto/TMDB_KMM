plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.yao.tmdb.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.yao.tmdb.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":kmm:data"))
    implementation(project(":kmm:domain"))
    implementation(project(":kmm:shared-ui"))
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.material)
    implementation(libs.compose.activity)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.precompose)
}